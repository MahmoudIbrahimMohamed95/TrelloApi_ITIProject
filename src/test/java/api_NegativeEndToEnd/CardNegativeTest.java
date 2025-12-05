package api_NegativeEndToEnd;

import apiClients.BoardApi;
import apiClients.CardApi;
import apiClients.ListApi;
import apiClients.OrganizationApi;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;


@Epic("Trello")
@Feature("API_Card Management")
@Story("Non Existing Card")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class CardNegativeTest {
    private JsonReader jsonReader;
    private String workspaceId;
    private String boardId;
    private String listId;
    private String cardId;

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
    @Description("Verify deleting board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard"})
    public void verifyDeletingBoard() {
       new BoardApi()
                .deleteBoard(boardId)
                .verifyBoardDeleted();
    }
    @Description("Verify retriving card after deleting board")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard","verifyDeletingBoard"})
    public void verifyRetrivingCardAfterDeletingBoard(){
        new CardApi()
                .getCard(cardId)
                .verifyCardNotFound();
    }
}
