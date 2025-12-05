package api_EndToEnd;

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
@Story("Card CRUD")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class CardTest {
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

    @Description("Verify retriving card")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList", "verifyCreatingCard"})
    public void verifyRetrivingCard(){
        new CardApi()
                .getCard(cardId)
                .verifyCardretriving(cardId, jsonReader.getJsonData("card-name"), listId);
    }
    @Description("Verify updating card")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard","verifyRetrivingCard"})
    public void verifyUpdatingCard() {
        new CardApi()
                .updateCard(cardId, jsonReader.getJsonData( "card-newName"))
                .verifyCardUpdating(cardId, jsonReader.getJsonData("card-newName"));
    }
    @Description("Verify retriving card after updating card")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard","verifyRetrivingCard","verifyUpdatingCard"})
    public void verifyRetrivingAfterUpdatingCard(){
        new CardApi()
                .getCard(cardId)
                .verifyCardRetrivingAfterUpdating(cardId, jsonReader.getJsonData("card-newName"));
    }
    @Description("Verify deleting card")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard",
                              "verifyRetrivingCard","verifyUpdatingCard","verifyRetrivingAfterUpdatingCard"})
    public void DeletingCard(){
        new CardApi()
                .deleteCard(cardId)
                .verifyCardDeleted();
    }
    @Description("Verify retriving card after deleting card")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard",
            "verifyRetrivingCard","verifyUpdatingCard","verifyRetrivingAfterUpdatingCard","DeletingCard"})
    public void verifyRetrivingCardAfterDeleting(){
        new CardApi()
                .getCard(cardId)
                .verifyCardRetrivingAfterDeleting();
    }
    @Description("Verify deleting workspace")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList","verifyCreatingCard",
            "verifyRetrivingCard","verifyUpdatingCard","verifyRetrivingAfterUpdatingCard","DeletingCard","verifyRetrivingCardAfterDeleting"})
    public void DeletingWorkspace() {
        new OrganizationApi()
                .deleteWorkSpace(workspaceId)
                .verifyWorkSpaceDeleted();
    }
}
