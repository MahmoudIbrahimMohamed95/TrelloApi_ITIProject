package api_NegativeEndToEnd;

import apiClients.OrganizationApi;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;

@Epic("Trello")
@Feature("API_Organization Management")
@Story("Missing Authorization in Request ")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class OrganizationNegativeTest {

    private JsonReader jsonReader;
    private String workspaceId;

    @BeforeClass
    protected void setUp() {
        jsonReader = new JsonReader("organization");
    }

    @Description("Error400")
    @Test(priority = 1)
    public void verifyWorkspaceCreation(){
    workspaceId=new OrganizationApi()
                .createWorkSpace(jsonReader.getJsonData("orgDisplayName"))
                .verifyWorkSpaceCreated(jsonReader.getJsonData("orgDisplayName"))
                .getResponse().jsonPath().get("id");
    }
    @Description("Error401")
    @Test(priority = 2)
    public void verifyAuthorizationFunctionality(){
        new OrganizationApi()
                .createWorkSpaceWithoutAuthorization(jsonReader.getJsonData("orgDisplayName"))
                .verifyUnauthorizedErrorGeneratedSuccessfully();
    }
    @Description("Error400")
    @Test(dependsOnMethods = {"verifyWorkspaceCreation"}, priority = 3)
    public void verifyDeletingNonExistingWorkspace(){
        new OrganizationApi()
                .deleteWorkSpace(workspaceId)
                .deleteWorkSpace(workspaceId)
                .verifyErrorNotFoundWorkingSuccessfully();
    }
}
