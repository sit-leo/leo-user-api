package app.leo.user.DTO;

public class Organization {

    private long id;
    private String name;
    private String description;
    private long organizationProfileId;

    public Organization() {
    }

    public Organization(String name, String description, long organizationProfileId) {
        this.name = name;
        this.description = description;
        this.organizationProfileId = organizationProfileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrganizationProfileId() {
        return organizationProfileId;
    }

    public void setOrganizationProfileId(long organizationProfileId) {
        this.organizationProfileId = organizationProfileId;
    }
}
