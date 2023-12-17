import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepairWorkDatabase {
    private RandomAccessFile dataFile;
    private Map<String, List<Long>> firmIndex;
    private Map<String, List<Long>> workTypeIndex;
    private Map<Date, List<Long>> executionDateIndex;

    public RepairWorkDatabase(String dataFilePath) throws IOException {
        dataFile = new RandomAccessFile(dataFilePath, "rw");
        firmIndex = new HashMap<>();
        workTypeIndex = new HashMap<>();
        executionDateIndex = new HashMap<>();
    }

    public void addRepairWork(RepairWork repairWork) throws IOException {
        // Запись в файл
        long position = dataFile.length();
        dataFile.seek(position);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(dataFile.getFD()));
        objectOutputStream.writeObject(repairWork);

        // Добавление в индексы
        addToIndex(firmIndex, repairWork.getFirm(), position);
        addToIndex(workTypeIndex, repairWork.getWorkType(), position);
        addToIndex(executionDateIndex, repairWork.getExecutionDate(), position);
    }

    public List<RepairWork> getAllRepairWorks() throws IOException, ClassNotFoundException {
        List<RepairWork> repairWorks = new ArrayList<>();
        dataFile.seek(0);

        while (dataFile.getFilePointer() < dataFile.length()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(dataFile.getFD()));
            RepairWork repairWork = (RepairWork) objectInputStream.readObject();
            repairWorks.add(repairWork);
        }

        return repairWorks;
    }

    public List<RepairWork> getRepairWorksByFirm(String firm) {
        return getObjectsByIndex(firmIndex, firm);
    }

    public List<RepairWork> getRepairWorksByWorkType(String workType) {
        return getObjectsByIndex(workTypeIndex, workType);
    }

    public List<RepairWork> getRepairWorksByExecutionDate(Date date) {
        return getObjectsByIndex(executionDateIndex, date);
    }

    public void removeRepairWork(int index) throws IOException, ClassNotFoundException {
        dataFile.seek(0);
        List<RepairWork> repairWorks = new ArrayList<>();

        while (dataFile.getFilePointer() < dataFile.length()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(dataFile.getFD()));
            RepairWork repairWork = (RepairWork) objectInputStream.readObject();
            repairWorks.add(repairWork);
        }

        if (index >= 0 && index < repairWorks.size()) {
            RepairWork removedWork = repairWorks.remove(index);

            // Обновление индексов
            removeFromIndex(firmIndex, removedWork.getFirm(), index);
            removeFromIndex(workTypeIndex, removedWork.getWorkType(), index);
            removeFromIndex(executionDateIndex, removedWork.getExecutionDate(), index);

            // Перезапись данных в файле
            dataFile.setLength(0);
            for (RepairWork work : repairWorks) {
                addRepairWork(work);
            }
        }
    }

    // Вспомогательные методы для работы с индексами
    private void addToIndex(Map<String, List<Long>> index, String key, long position) {
        if (!index.containsKey(key)) {
            index.put(key, new ArrayList<>());
        }
        index.get(key).add(position);
    }

    private void addToIndex(Map<Date, List<Long>> index, Date key, long position) {
        if (!index.containsKey(key)) {
            index.put(key, new ArrayList<>());
        }
        index.get(key).add(position);
    }
//
    private <K> List<RepairWork> getObjectsByIndex(Map<K, List<Long>> index, K key) {
        List<RepairWork> repairWorks = new ArrayList<>();
        List<Long> positions = index.get(key);

        if (positions != null) {
            try {
                for (long position : positions) {
                    dataFile.seek(position);
                    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(dataFile.getFD()));
                    RepairWork repairWork = (RepairWork) objectInputStream.readObject();
                    repairWorks.add(repairWork);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return repairWorks;
    }

    private <K> void removeFromIndex(Map<K, List<Long>> index, K key, int indexToRemove) {
        List<Long> positions = index.get(key);

        if (positions != null) {
            positions.remove(indexToRemove);

            if (positions.isEmpty()) {
                index.remove(key);
            }
        }
    }
}
