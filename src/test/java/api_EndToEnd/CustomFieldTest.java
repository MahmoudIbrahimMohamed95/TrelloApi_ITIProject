package api_EndToEnd;

import apiClients.BoardApi;
import apiClients.CustomFieldApi;
import apiClients.OrganizationApi;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;

/*
**Class Skipped as PowerUp Enable Api is depricated and CustomField PowerUps is disabled
 */

@Epic("Trello")
@Feature("API_CustomField Management")
@Story("Custom Field on Board")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

@Test(enabled = false)
public class CustomFieldTest {
    private JsonReader jsonReader;
    private String workspaceId;
    private String boardId;

    @BeforeClass
    protected void setUp() {
        jsonReader = new JsonReader("customField");
    }

    @Description("Verify creating workspace")
    @Test
    public void verifyCreatingWorkspace() {
        workspaceId = new OrganizationApi()
                .createWorkSpace(jsonReader.getJsonData("customField-spaceName"))
                .verifyWorkSpaceCreated(jsonReader.getJsonData("customField-spaceName"))
                .response.jsonPath().getString("id");
    }

    @Description("Verify creating board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace"})
    public void verifyCreatingBoard() {
        boardId = new BoardApi()
                .createBoard(jsonReader.getJsonData("customField-boardName"), workspaceId)
                .verifyBoardCreated(jsonReader.getJsonData("customField-boardName"), workspaceId)
                .getResponse().jsonPath().getString("id");
    }

    @Description("Verify creating custom field")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace", "verifyCreatingBoard"})
    public void verifyCreatingCustomField() {
        new BoardApi().enablePowerUpOnBoard(boardId);
        new CustomFieldApi()
                .createCustomFieldOnBoard(boardId, jsonReader.getJsonData("customField-modelType"),
                        jsonReader.getJsonData("customField-name"), jsonReader.getJsonData("customField-type"),
                        jsonReader.getJsonData("customField-pos"));
    }
}