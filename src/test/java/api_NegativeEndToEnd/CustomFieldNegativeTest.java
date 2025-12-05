package api_NegativeEndToEnd;

import apiClients.BoardApi;
import apiClients.CustomFieldApi;
import apiClients.OrganizationApi;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;
@Epic("Trello")
@Feature("API_CustomField Management")
@Story("Missing Parameter in Request ")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class CustomFieldNegativeTest {
    private JsonReader jsonReader;
    private String workspaceId;
    private String boardId;

    @BeforeClass
    protected void setUp() {
        jsonReader = new JsonReader("customField");
    }

    @Description("Verify creating Workspace")
    @Test
    public void verifyCreatingWorkspace() {
        workspaceId = new OrganizationApi()
                .createWorkSpace(jsonReader.getJsonData("customField-spaceName"))
                .verifyWorkSpaceCreated(jsonReader.getJsonData("customField-spaceName"))
                .response.jsonPath().getString("id");
    }
    @Description("Verify creating Board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace"})
    public void verifyCreatingBoard() {
        boardId = new BoardApi()
                .createBoard(jsonReader.getJsonData("customField-boardName"), workspaceId)
                .verifyBoardCreated(jsonReader.getJsonData("customField-boardName"), workspaceId)
                .getResponse().jsonPath().getString("id");
    }

    @Description("Verify creating Custom Field with missing parameter")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace", "verifyCreatingBoard"})
    public void verifyCreatingCustomFieldWithMissingParameter() {
        new BoardApi().enablePowerUpOnBoard(boardId);
        new CustomFieldApi()
                .createCustomFieldOnBoardWithMissingParameter( jsonReader.getJsonData("customField-modelType"),
                        jsonReader.getJsonData("customField-name"), jsonReader.getJsonData("customField-type"),
                        jsonReader.getJsonData("customField-pos"))
                .verifyErrorMissingBodyMessage(jsonReader.getJsonData("customField-invalidMessage"));
    }

}
