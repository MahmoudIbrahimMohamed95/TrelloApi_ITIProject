package api_NegativeEndToEnd;

import apiClients.BoardApi;
import apiClients.ListApi;
import apiClients.OrganizationApi;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;

@Epic("Trello")
@Feature("API_List Management")
@Story("Repeating Data in Request ")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class ListNegativeTest {
    private JsonReader jsonReader;
    private String workspaceId;
    private String boardId;
    private String listId;
    private String listIdII;
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
    @Description("Verify creating list with same data")
    @Test(dependsOnMethods = {"verifyCreatingWorkspace","verifyCreatingBoard","verifyCreatingList"})
    public void verifyCreatingListApiWithSameData(){
        listIdII=new ListApi()
                .createList( boardId, jsonReader.getJsonData("list-name"))
                .verifyListApiResponse(listId)
                .getResponse().jsonPath().getString("id");
    }
}
