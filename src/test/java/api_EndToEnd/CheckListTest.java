package api_EndToEnd;

import apiClients.*;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;


@Epic("Trello")
@Feature("API_CheckList Management")
@Story("CHeckList CRUD")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class CheckListTest {
    private JsonReader jsonReader;
    private String workspaceId;
    private String boardId;
    private String listId;
    private String cardId;
    private String checkListId;

    @BeforeClass
    protected void setUp() {
        jsonReader = new JsonReader("checklist");
    }
    @Description("Verify creating workspace")
    @Test
    public void verifyCreatingWorkspace(){
        workspaceId=new OrganizationApi()
                .createWorkSpace(jsonReader.getJsonData("checklist-spaceName"))
                .verifyWorkSpaceCreated(jsonReader.getJsonData("checklist-spaceName"))
                .response.jsonPath().getString("id");
    }
    @Description("Verify creating board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace"})
    public void verifyCreatingBoard(){
        boardId=new BoardApi()
                .createBoard(jsonReader.getJsonData("checklist-boardName"), workspaceId, jsonReader.getJsonData("checklist-boardPowerUP"))
                .verifyBoardCreated(jsonReader.getJsonData("checklist-boardName"), workspaceId)
                .getResponse().jsonPath().getString("id");
    }
    @Description("Verify creating list")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard"})
    public void verifyCreatingList(){
        listId=new ListApi()
                .createList( boardId, jsonReader.getJsonData("checklist-listName"))
                .verifyListCreated(jsonReader.getJsonData("checklist-listName"), boardId)
                .getResponse().jsonPath().getString("id");
    }
    @Description("Verify creating card")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList"})
    public void verifyCreatingCard(){
        cardId=new CardApi()
                .createCard( listId, jsonReader.getJsonData("checklist-cardName"))
                .verifyCardCreated(jsonReader.getJsonData("checklist-cardName"), listId)
                .getResponse().jsonPath().getString("id");
    }
    @Description("Verify creating checkList")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard"})
    public void verifyCreatingCheckList(){
        checkListId=new CheckListApi()
                .createCheckList( cardId, jsonReader.getJsonData("checklist-name"))
                .verifyCheckListCreated(jsonReader.getJsonData("checklist-name"), cardId)
                .getResponse().jsonPath().getString("id");
    }

    @Description("Verify retriving checkList")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard","verifyCreatingCheckList"})
    public void verifyRetrivingCheckList(){
        new CheckListApi()
                .getCheckList(checkListId)
                .verifyCheckListretriving(checkListId, jsonReader.getJsonData("checklist-name"), cardId);
    }
    @Description("Verify updating checkList")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard","verifyRetrivingCheckList"})
    public void verifyUpdatingCheckList() {
        new CheckListApi()
                .updateCheckList(checkListId,  jsonReader.getJsonData("checklist-newName"))
                .verifyCheckListUpdating(checkListId, jsonReader.getJsonData("checklist-newName"));
    }
    @Description("Verify retriving checkList after updating")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList",
                               "verifyCreatingCard","verifyRetrivingCheckList","verifyUpdatingCheckList"})
    public void verifyRetrivingAfterUpdatingCheckList(){
        new CheckListApi()
                .getCheckList(checkListId)
                .verifyCheckListRetrivingAfterUpdating(checkListId, jsonReader.getJsonData("checklist-newName"));
    }
    @Description("Verify deleting checkList")
    @Test(dependsOnMethods ={"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList",
            "verifyCreatingCard","verifyRetrivingCheckList","verifyUpdatingCheckList","verifyRetrivingAfterUpdatingCheckList"})
    public void DeletingCheckList(){
        new CheckListApi()
                .deleteCheckList(checkListId)
                .verifyCheckListDeleted();
    }
    @Description("Verify retriving checkList after deleting")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard"
                                ,"verifyRetrivingCheckList","verifyUpdatingCheckList","verifyRetrivingAfterUpdatingCheckList","DeletingCheckList"})
    public void verifyRetrivingCheckListAfterDeleting(){
        new CheckListApi()
                .getCheckList(checkListId)
                .verifyCheckListRetrivingAfterDeleting();
    }
    @Description("Verify deleting workspace")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard"
            ,"verifyRetrivingCheckList","verifyUpdatingCheckList","verifyRetrivingAfterUpdatingCheckList","DeletingCheckList","verifyRetrivingCheckListAfterDeleting"})
    public void DeletingWorkspace() {
        new OrganizationApi()
                .deleteWorkSpace(workspaceId)
                .verifyWorkSpaceDeleted();
    }
}
