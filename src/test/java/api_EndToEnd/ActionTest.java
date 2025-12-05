package api_EndToEnd;

import apiClients.*;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;


@Epic("Trello")
@Feature("API_Actions Management")
@Story("Comment Process")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class ActionTest {
    private JsonReader jsonReader;
    private String workspaceId;
    private String boardId;
    private String listId;
    private String cardId;
    private String commentId;
    private String actionId;

    @BeforeClass
    protected void setUp() {
        jsonReader = new JsonReader("actions");
    }
    @Description("Verify creating workspace")
    @Test
    public void verifyCreatingWorkspace(){
        workspaceId=new OrganizationApi()
                .createWorkSpace(jsonReader.getJsonData("actions-spaceName"))
                .verifyWorkSpaceCreated(jsonReader.getJsonData("actions-spaceName"))
                .getResponse().jsonPath().getString("id");
    }
    @Description("Verify creating board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace"})
    public void verifyCreatingBoard(){
        boardId=new BoardApi()
                .createBoard(jsonReader.getJsonData("actions-boardName"), workspaceId, jsonReader.getJsonData("actions-boardPowerUp"))
                .verifyBoardCreated(jsonReader.getJsonData("actions-boardName"), workspaceId)
                .getResponse().jsonPath().getString("id");
    }

    @Description("Verify creating list")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard"})
    public void verifyCreatingList(){
        listId=new ListApi()
                .createList( boardId, jsonReader.getJsonData("actions-listName"))
                .verifyListCreated(jsonReader.getJsonData("actions-listName"), boardId)
                .getResponse().jsonPath().getString("id");
    }

    @Description("Verify creating card")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList"})
    public void verifyCreatingCard(){
        cardId=new CardApi()
                .createCard( listId, jsonReader.getJsonData("actions-cardName"))
                .verifyCardCreated(jsonReader.getJsonData("actions-cardName"), listId)
                .getResponse().jsonPath().getString("id");
    }

    @Description("Verify commment on card")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard"})
    public void verifyCommentOnCard() {
        commentId = new CardApi()
                .commentOnCard(cardId, jsonReader.getJsonData("actions-cardComment"))
                .verifyCommentOnCard(cardId, jsonReader.getJsonData("actions-cardComment"))
                .getResponse().jsonPath().getString("id");
    }

    @Description("Verify retrive commment on card")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard","verifyCommentOnCard"})
    public void verifyRetriveCommentOnCard() {
        actionId=new CardApi()
                .getCommentActionsOnCard(cardId)
                .verifyRetrivingComments(cardId, jsonReader.getJsonData("actions-cardComment"))
                .getResponse().jsonPath().getString("[0].id");
        }

    @Description("Verify update commment on card")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList", "verifyCreatingCard","verifyCommentOnCard","verifyRetriveCommentOnCard"})
    public void verifyUpdateCommentOnCard(){
        new ActionApi()
                .updateAction(commentId, jsonReader.getJsonData("actions-cardCommentUpdate"))
               .verifyUpdatingAction(commentId, jsonReader.getJsonData("actions-cardCommentUpdate"));
        }
    @Description("Verify retrive commment after update")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList", "verifyCreatingCard","verifyCommentOnCard","verifyRetriveCommentOnCard","verifyUpdateCommentOnCard"})
    public void verifyRetriveCommentAfterUpdate(){
        new ActionApi()
                .getAction(commentId)
                .verifyRetrivingAfterUpdatingAction(commentId, jsonReader.getJsonData("actions-cardCommentUpdate"));
    }
    @Description("Verify delete comment")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList", "verifyCreatingCard","verifyCommentOnCard","verifyRetriveCommentOnCard"
                               ,"verifyUpdateCommentOnCard","verifyRetriveCommentAfterUpdate"})
    public void verifyDeleteComment(){
        new ActionApi()
                .deleteAction(commentId)
                .verifydeleteAction();
    }
    @Description("Verify retrive comment after delete")

    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList", "verifyCreatingCard","verifyCommentOnCard","verifyRetriveCommentOnCard"
            ,"verifyUpdateCommentOnCard","verifyRetriveCommentAfterUpdate","verifyDeleteComment"})
    public void verifyRetriveCommentAfterDelete(){
        new ActionApi()
                .getAction(commentId)
                .verifyRetriveAfterdeleteAction();
    }
}
