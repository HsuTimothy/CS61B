package db61b;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/*
 * @author Michael Ferrin
 *
 * Tests for the functioinality of:
 * 1. The row class
 * 2. The table constructor
 * 3. The table class
 * 4. The database class
 * 5. Run tests on Column to better understand it
 */

public class BasicTests {

    @Test
    public void testRow() {
        Row r = new Row(new String[]{"Lets", "hope",
                                     "this", "test", "passes."});
        Row r1 = new Row(new String[]{"Lets", "hi", "this", "test", "passes."});
        Row r2 = new Row(new String[]{"Lts", "hope", "this", "test"});
        assertEquals(5, r.size());
        assertEquals("hope", r.get(1));
        assertEquals(true, r.equals(r));
        assertEquals(false, r.equals(r1));
        assertEquals(false, r.equals(r2));
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void textExpectedException() {
        exception.expect(DBException.class);
        List<String> newTable = Arrays.asList("one", "one", "two", "three");
        Table t = new Table(newTable);
        assertEquals(3, t.columns());
    }

    @Test
    public void testTable() {
        List<String> newTable = Arrays.asList("one", "two", "three");
        Table t = new Table(newTable);
        assertEquals(3, t.columns());
        assertEquals("two", t.getTitle(1));
        assertEquals(2, t.findColumn("three"));
        Row myRow = new Row(new String[]{"jack", "john", "james"});
        assertEquals(true, t.add(myRow));
        assertEquals(false, t.add(myRow));
        assertEquals(1, t.size());
        Row myNewRow = new Row(new String[]{"ron", "paul", "howard"});
        t.add(myNewRow);
        Row rowForSelect = new Row(new String[]{"hey", "there", "kid"});
        t.add(rowForSelect);
        List<String> cn = Arrays.asList("two", "three");

        Column c1 = new Column("one", t);
        Condition cond1 = new Condition(c1, "=", "jack");
        List<Condition> condList = new ArrayList<Condition>();
        condList.add(cond1);

        List<Condition> con = null;
        Table printMe = t.select(cn, condList);
    }

    @Test
    public void testDatabase() {
        List<String> newTable = Arrays.asList("one", "two", "three");
        Table t = new Table(newTable);
        Database d = new Database();
        d.put("myTable", t);
        d.get("myTable");
    }

    @Test
    public void testColumn() {
        List<String> newTable = Arrays.asList("one", "two", "three");
        Table t = new Table(newTable);
        Column c = new Column("three", t);
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(BasicTests.class));
    }
}


