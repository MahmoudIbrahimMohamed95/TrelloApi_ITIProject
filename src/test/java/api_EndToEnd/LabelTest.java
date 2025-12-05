package api_EndToEnd;

import apiClients.BoardApi;
import apiClients.LabelApi;
import apiClients.OrganizationApi;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;

@Epic("Trello")
@Feature("API_Label Management")
@Story("Label CRUD")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class LabelTest {
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
    @Description("Verify creating label on board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard"})
    public void verifyCreatingLabelOnBoard(){
        labelId=new LabelApi()
                .createLabel(boardId,jsonReader.getJsonData("label-name"),jsonReader.getJsonData("label-color"))
                .verifyLabelCreated(jsonReader.getJsonData("label-name"), boardId)
                .getResponse().jsonPath().getString("id");
    }
    @Description("Verify retriving label")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingLabelOnBoard"})
    public void verifyRetrivingLabel() {
        new LabelApi()
                .getLabel(labelId)
                .verifyLabelRetriving(labelId, jsonReader.getJsonData("label-name"), boardId);
    }
    @Description("Verify updating label")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingLabelOnBoard","verifyRetrivingLabel"})
    public void verifyLabelUpdating() {
        new LabelApi()
                .updateLabel(labelId, jsonReader.getJsonData("label-newName"))
                .verifyLabelUpdating(labelId, jsonReader.getJsonData("label-newName"));
    }
    @Description("Verify deleting label")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingLabelOnBoard","verifyRetrivingLabel","verifyLabelUpdating"})
    public void verifyLabelDeleting() {
        new LabelApi()
                .deleteLabel(labelId)
                .verifyLabelDeleted();
    }
    @Description("Verify retriving label after deleting")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingLabelOnBoard","verifyRetrivingLabel","verifyLabelUpdating","verifyLabelDeleting"})
    public void verifyRetrivingLabelAfterDeleting() {
        new LabelApi()
                .getLabel(labelId)
                .verifyLabelRetrivingAfterDeleting();
    }
}
