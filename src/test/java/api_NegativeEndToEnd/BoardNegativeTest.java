package api_NegativeEndToEnd;

import apiClients.BoardApi;
import apiClients.OrganizationApi;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import readers.JsonReader;

@Epic("Trello")
@Feature("API_Board Management")
@Story("Non Existing Board")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mahmoud")

public class BoardNegativeTest {
    private JsonReader jsonReader;
    private String workspaceId;
    private String boardId;

    @BeforeClass
    protected void setUp() {
        jsonReader = new JsonReader("board");
    }

    @Description("Verify that user can't retrieve non-existing board")
    @Test(priority = 1)
    public void verifyRetrivingNonExistingBoard(){
        new BoardApi()
                .getBoard(jsonReader.getJsonData("board-inValidID"))
                .verifyNonExistingBoardManipulating();
    }

    @Description("Verify that user can't update non-existing board")
    @Test(priority = 2)
    public void verifyUpdatingNonExistingBoard() {
        new BoardApi()
                .updateBoard(boardId, jsonReader.getJsonData("board-inValidID"))
                .verifyNonExistingBoardManipulating();
    }
}
