public class Task {
    protected String name;
    protected String status;
    protected int id = 0;
    protected String additionalInformation;

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


    Task (String name, String additionalInformation) {
        this.name = name;
        this.status = "NEW";
        this.additionalInformation = additionalInformation;
    }

    Task (String name, String additionalInformation, String status) {
        this.name = name;
        this.additionalInformation = additionalInformation;
        this.status = status;
        this.id = id;
    }
}