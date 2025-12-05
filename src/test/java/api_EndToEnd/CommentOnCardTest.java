package api_EndToEnd;

import apiClients.*;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;


@Epic("Trello")
@Feature("API_Comment Management")
@Story("Commment On Card")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class CommentOnCardTest {
    private JsonReader jsonReader;
    private String workspaceId;
    private String boardId;
    private String listId;
    private String cardId;
    private String commentId;
    @BeforeClass
    protected void setUp() {
        jsonReader = new JsonReader("card");
    }
    @Description("Verify creating workspace")
    @Test
    public void verifyCreatingWorkspace(){
        workspaceId=new OrganizationApi()
                .createWorkSpace(jsonReader.getJsonData("card-spaceName"))
                .verifyWorkSpaceCreated(jsonReader.getJsonData("card-spaceName"))
                .response.jsonPath().getString("id");
    }
    @Description("Verify creating board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace"})
    public void verifyCreatingBoard(){
        boardId=new BoardApi()
                .createBoard(jsonReader.getJsonData("card-boardName"), workspaceId, jsonReader.getJsonData("card-boardPowerUP"))
                .verifyBoardCreated(jsonReader.getJsonData("card-boardName"), workspaceId)
                .getResponse().jsonPath().getString("id");
    }
    @Description("Verify creating list")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard"})
    public void verifyCreatingList(){
        listId=new ListApi()
                .createList( boardId, jsonReader.getJsonData("card-listName"))
                .verifyListCreated(jsonReader.getJsonData("card-listName"), boardId)
                .getResponse().jsonPath().getString("id");
    }
    @Description("Verify creating card")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList"})
    public void verifyCreatingCard(){
        cardId=new CardApi()
                .createCard( listId, jsonReader.getJsonData("card-name"))
                .verifyCardCreated(jsonReader.getJsonData("card-name"), listId)
                .getResponse().jsonPath().getString("id");
    }
    @Description("Verify comment on card")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard"})
    public void verifyCommentOnCard() {
        commentId = new CardApi()
                .commentOnCard(cardId, jsonReader.getJsonData("card-comment"))
                .verifyCommentOnCard(cardId, jsonReader.getJsonData("card-comment"))
                .getResponse().jsonPath().getString("id");
    }
    @Description("Verify retrive comment action on card")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard","verifyCommentOnCard"})
    public void verifyRetriveCommentActionOnCard() {
         new ActionApi()
                .getSpecificAction(commentId, jsonReader.getJsonData("card-commentActionfield"))
                .verifyActionField(cardId, jsonReader.getJsonData("card-comment"));
    }
    @Description("Verify remove comment on card")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList",
            "verifyCreatingCard","verifyCommentOnCard","verifyRetriveCommentActionOnCard"})
    public void verifyRemoveCommentOnCard() {
        new CardApi()
                .deleteCommentOnCard(cardId, commentId)
                .verifyCardCommentDeleted();
    }
    @Description("Verify retrive comment after delete")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList",
            "verifyCreatingCard","verifyCommentOnCard","verifyRetriveCommentActionOnCard","verifyRemoveCommentOnCard"})
    public void verifyRetriveCommentAfterDelete() {
        new ActionApi()
                .getSpecificAction(commentId, jsonReader.getJsonData("card-commentActionfield"))
                .verifyActionFieldAfterDeleting();
    }
    @Description("Verify delete workspace")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList",
                                "verifyCreatingCard","verifyCommentOnCard","verifyRetriveCommentActionOnCard","verifyRemoveCommentOnCard","verifyRetriveCommentAfterDelete"})
    public void deleteWorkSpace() {
        new OrganizationApi()
                .deleteWorkSpace(workspaceId)
                .verifyWorkSpaceDeleted();
    }

}