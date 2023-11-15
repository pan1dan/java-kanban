public class Task {
    protected String name;
    protected String status;
    protected int id = 0;
    protected String additionalInformation;
    protected static int counterID = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public static int getCounterID() {
        return counterID;
    }

    public static void setCounterID(int counterID) {
        Task.counterID = counterID;
    }

    Task (String name, String additionalInformation) {
        this.name = name;
        this.status = "NEW";
        this.additionalInformation = additionalInformation;
        setID(counterID);

        counterID++;
    }

    Task (String name, String additionalInformation, String status) {
        this.name = name;
        this.additionalInformation = additionalInformation;
        this.status = status;
        setID(counterID);

        counterID++;
    }
}