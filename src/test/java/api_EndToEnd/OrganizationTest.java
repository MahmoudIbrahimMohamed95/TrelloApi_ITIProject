package api_EndToEnd;

import apiClients.OrganizationApi;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;

@Epic("Trello")
@Feature("API_Organization Management")
@Story("Organization CRUD")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")


public class OrganizationTest {

    private JsonReader jsonReader;
    private String workspaceId;

    @BeforeClass
    protected void setUp() {
        jsonReader = new JsonReader("organization");
    }
    @Description("Verify creating workspace")
    @Test
    public void verifyCreatingWorkspace(){
    workspaceId=new OrganizationApi()
                .createWorkSpace(jsonReader.getJsonData("orgDisplayName"))
                .verifyWorkSpaceCreated(jsonReader.getJsonData("orgDisplayName"))
                .getResponse().jsonPath().getString("id");
    }
    @Description("Verify retriving workspace")
    @Test(dependsOnMethods = "verifyCreatingWorkspace")
    public void verifyRetrivingWorkspace(){
        new OrganizationApi()
                .getWorkSpace(workspaceId)
                .verifyWorkSpaceRetriving(workspaceId,jsonReader.getJsonData("orgDisplayName"));
    }
    @Description("Verify updating workspace")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyRetrivingWorkspace"})
    public void verifyUpdatingWorkspace(){
        new OrganizationApi()
                .updateWorkSpace(workspaceId,jsonReader.getJsonData("orgUpdateName"))
                .verifyWorkSpaceUpdated(workspaceId,jsonReader.getJsonData("orgUpdateName"));
    }
    @Description("Verify retriving workspace after updating")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyRetrivingWorkspace","verifyUpdatingWorkspace"})
    public void verifyRetrivingAfterUpdatingWorkspace(){
        new OrganizationApi()
                .getWorkSpace(workspaceId)
                .verifyWorkSpaceRetrivingAfterUpdating(workspaceId,jsonReader.getJsonData("orgUpdateName"));
    }
    @Description("Verify deleting workspace")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyRetrivingWorkspace","verifyUpdatingWorkspace","verifyRetrivingAfterUpdatingWorkspace"})
    public void verifyDeletingWorkspace(){
        new OrganizationApi()
                .deleteWorkSpace(workspaceId)
                .verifyWorkSpaceDeleted();
    }
    @Description("Verify retriving workspace after deleting")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyRetrivingWorkspace","verifyUpdatingWorkspace","verifyRetrivingAfterUpdatingWorkspace","verifyDeletingWorkspace"})
    public void verifyRetrivingAfterDeletingWorkspace(){
        new OrganizationApi()
                .getWorkSpace(workspaceId)
                .verifyWorkSpaceRetrivingAfterDeleting();
    }
}