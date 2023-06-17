package database;

import model.Worker;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class OfficeRepository {
    private Table workerTable;
    private Table phoneTable;

    public OfficeRepository(Table workerTable, Table phoneTable) {
        this.workerTable = workerTable;
        this.phoneTable = phoneTable;
    }

    // get all workers

    public List<Worker> getAllWorkers() {
        return workerTable
                .selectAll()
                .entrySet()
                .stream()
                .map(workerResultSet ->
                        extractWorkerFromResultSet(workerResultSet.getKey(), workerResultSet.getValue()))
                .toList();
    }

    public Optional<Worker> getWorkerByName(String name) {
        Optional<Worker> foundWorker =
                workerTable
                .selectAll()
                .entrySet()
                .stream()
                .filter(workerResultSet->workerResultSet.getValue().get("name").equals(name))
                .map(workerResultSet ->
                        extractWorkerFromResultSet(workerResultSet.getKey(), workerResultSet.getValue()))
                .findFirst();
        return foundWorker;
    }

    public Worker getWorkerById(String id) {
        Map<String, String> resultSet = workerTable
                .selectRowById(id);
        return extractWorkerFromResultSet(id, resultSet);
    }

    // get exact worker
    // get all phoneIds

    private Worker extractWorkerFromResultSet(String workerId, Map<String, String> workerResultSet) {

        String name = workerResultSet.get("name");

        List<String> phoneNumbers = phoneTable
                .selectAll()
                .values()
                .stream()
                .filter(phoneResultSet -> phoneResultSet.get("worker_id").equals(workerId))
                .map(phoneResultSet -> phoneResultSet.get("phone_number"))
                .toList();
        return new Worker(workerId, name, phoneNumbers);
    }
}
