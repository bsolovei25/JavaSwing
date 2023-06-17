package database;

import java.util.List;

public class OfficeDataBaseFabric {
    public static Table createEmptyOfficeTable() {
        List<String> officeWorker = List.of("name");
        return new Table(officeWorker);
    }

    public static Table createEmptyPhoneNumbersTable() {
        List<String> officeWorker = List.of("worker_id", "phone_number");
        return new Table(officeWorker);
    }

    public static Table createOfficeTableWithTestData() {
        Table officeTable = createEmptyOfficeTable();

        officeTable.insertRow(List.of("Vlad"));
        officeTable.insertRow(List.of("Bohdan"));

        return officeTable;
    }

    public static Table createPhoneNumberTable() {
        Table officeTable = createEmptyPhoneNumbersTable();

        officeTable.insertRow(List.of("1", "8883355536"));
        officeTable.insertRow(List.of("1", "356789232"));
        officeTable.insertRow(List.of("1", "11123782"));
        officeTable.insertRow(List.of("2", "231890022"));
        officeTable.insertRow(List.of("2", "9280232718"));

        return officeTable;
    }
}
