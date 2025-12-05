package api_EndToEnd;

import apiClients.BoardApi;
import apiClients.OrganizationApi;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;

@Epic("Trello")
@Feature("API_Board Management")
@Story("Board CRUD")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class BoardTest {
    private JsonReader jsonReader;
    private String workspaceId;
    private String boardId;

    @BeforeClass
    protected void setUp() {
        jsonReader = new JsonReader("board");
    }
    @Description("Verify creating workspace")
    @Test
    public void verifyCreatingWorkspace(){
        workspaceId=new OrganizationApi()
                .createWorkSpace(jsonReader.getJsonData("board-spaceName"))
                .verifyWorkSpaceCreated(jsonReader.getJsonData("board-spaceName"))
                .response.jsonPath().getString("id");
    }
    @Description("Verify creating board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace"})
    public void verifyCreatingBoard(){
        boardId=new BoardApi()
                  .createBoard(jsonReader.getJsonData("board-name"), workspaceId, jsonReader.getJsonData("board-PowerUP"))
                  .verifyBoardCreated(jsonReader.getJsonData("board-name"), workspaceId)
                  .getResponse().jsonPath().getString("id");
    }
    @Description("Verify retriving board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard"})
    public void verifyRetrivingBoard(){
        new BoardApi()
                .getBoard(boardId)
                .verifyBoardretriving(boardId, jsonReader.getJsonData("board-name"), workspaceId);
    }
    @Description("Verify updating board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyRetrivingBoard"})
    public void verifyUpdatingBoard() {
        new BoardApi()
                .updateBoard(boardId, jsonReader.getJsonData("board-newName"))
                .verifyBoardUpdating(boardId, jsonReader.getJsonData("board-newName"));
    }
    @Description("Verify retriving board after updating")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyRetrivingBoard","verifyUpdatingBoard"})
    public void verifyRetrivingAfterUpdatingBoard(){
        new BoardApi()
                .getBoard(boardId)
                .verifyBoardRetrivingAfterUpdating(boardId, jsonReader.getJsonData("board-newName"));
    }
    @Description("Verify deleting board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyRetrivingBoard","verifyUpdatingBoard","verifyRetrivingAfterUpdatingBoard"})
    public void verifyDeletingBoard(){
        new BoardApi()
                .deleteBoard(boardId)
                .verifyBoardDeleted();
    }
    @Description("Verify retriving board after deleting")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyRetrivingBoard","verifyUpdatingBoard","verifyRetrivingAfterUpdatingBoard","verifyDeletingBoard"})
    public void verifyRetrivingAfterDeletingBoard(){
        new BoardApi()
                .getBoard(boardId)
                .verifyBoardRetrivingAfterDeleting();
    }
}
