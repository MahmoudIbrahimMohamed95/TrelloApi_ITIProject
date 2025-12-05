package api_EndToEnd;

import apiClients.BoardApi;
import apiClients.ListApi;
import apiClients.OrganizationApi;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;

@Epic("Trello")
@Feature("API_List Management")
@Story("List CRUD")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class ListTest {
    private JsonReader jsonReader;
    private String workspaceId;
    private String boardId;
    private String listId;
    @BeforeClass
    protected void setUp() {
        jsonReader = new JsonReader("list");
    }
 @Description("Verify creating workspace")
    @Test
    public void verifyCreatingWorkspace(){
        workspaceId=new OrganizationApi()
                .createWorkSpace(jsonReader.getJsonData("list-spaceName"))
                .verifyWorkSpaceCreated(jsonReader.getJsonData("list-spaceName"))
                .response.jsonPath().getString("id");
    }
    @Description("Verify creating board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace"})
    public void verifyCreatingBoard(){
        boardId=new BoardApi()
                .createBoard(jsonReader.getJsonData("list-boardName"), workspaceId, jsonReader.getJsonData("list-boardPowerUP"))
                .verifyBoardCreated(jsonReader.getJsonData("list-boardName"), workspaceId)
                .getResponse().jsonPath().getString("id");
    }
    @Description("Verify creating list")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard"})
    public void verifyCreatingList(){
        listId=new ListApi()
                .createList( boardId, jsonReader.getJsonData("list-name"))
                .verifyListCreated(jsonReader.getJsonData("list-name"), boardId)
                .getResponse().jsonPath().getString("id");
    }
    @Description("Verify retriving list")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList"})
    public void verifyRetrivingList(){
        new ListApi()
                .getList(listId)
                .verifyListretriving(listId, jsonReader.getJsonData("list-name"), boardId);
    }
    @Description("Verify updating list")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyRetrivingList"})
    public void verifyUpdatingList() {
        new ListApi()
                .updateList(listId, jsonReader.getJsonData("list-newName"))
                .verifyListUpdating(listId, jsonReader.getJsonData("list-newName"));
    }
    @Description("Verify retriving list after updating")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyRetrivingList","verifyUpdatingList"})
    public void verifyRetrivingAfterUpdatingList(){
        new ListApi()
                .getList(listId)
                .verifyListRetrivingAfterUpdating(listId, jsonReader.getJsonData("list-newName"));
    }
    @Description("Verify deleting board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyRetrivingList",
            "verifyUpdatingList","verifyRetrivingAfterUpdatingList"})
    public void DeletingBoard(){
        new BoardApi()
                .deleteBoard(boardId)
                .verifyBoardDeleted();
    }
    @Description("Verify retriving list after deleting board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyRetrivingList",
            "verifyUpdatingList","verifyRetrivingAfterUpdatingList","DeletingBoard"})
    public void verifyRetrivingListAfterDeletingBoard(){
        new ListApi()
                .getList(listId)
                .verifyListRetrivingAfterDeleting();
    }

    @Description("Verify deleting workspace")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyRetrivingList",
            "verifyUpdatingList","verifyRetrivingAfterUpdatingList","DeletingBoard","verifyRetrivingListAfterDeletingBoard"})
    public void DeletingWorkspace() {
        new OrganizationApi()
                .deleteWorkSpace(workspaceId)
                .verifyWorkSpaceDeleted();
    }
}
