package api_NegativeEndToEnd;

import apiClients.BoardApi;
import apiClients.LabelApi;
import apiClients.OrganizationApi;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;

@Epic("Trello")
@Feature("API_Label Management")
@Story("Missing Parameter in Request ")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class LabelNegativeTest {

    private JsonReader jsonReader;
    private String workspaceId;
    private String boardId;
    private String labelId;

    @BeforeClass
    protected void setUp() {
        jsonReader = new JsonReader("label");
    }
    @Description("Verify creating workspace")
    @Test
    public void verifyCreatingWorkspace(){
        workspaceId=new OrganizationApi()
                .createWorkSpace(jsonReader.getJsonData("label-spaceName"))
                .verifyWorkSpaceCreated(jsonReader.getJsonData("label-spaceName"))
                .response.jsonPath().getString("id");
    }
    @Description("Verify creating board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace"})
    public void verifyCreatingBoard(){
        boardId=new BoardApi()
                .createBoard(jsonReader.getJsonData("label-boardName"), workspaceId, jsonReader.getJsonData("label-boardPowerUP"))
                .verifyBoardCreated(jsonReader.getJsonData("label-boardName"), workspaceId)
                .getResponse().jsonPath().getString("id");
    }
    @Description("Verify creating label on board with missing parameter")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard"})
    public void verifyCreatingLabelOnBoardWithMissingParameter(){
    new LabelApi()
            .createLabelWithMissingParameter(boardId,jsonReader.getJsonData("label-color"))
            .verifyBadRequestError();
    }
}