package db61b;

import java.util.HashMap;

/** A collection of Tables, indexed by name.
 *  @author Michael Ferrin*/
class Database {
    /** An empty database. */
    public Database() {
        HashMap<String, Table> daatabase = null;
    }

    /** Return the Table whose name is NAME stored in this database, or null
     *  if there is no such table. */
    public Table get(String name) {
        if (_database.get(name) == null) {
            return null;
        }
        return _database.get(name);
    }

    /** Set or replace the table named NAME in THIS to TABLE.  TABLE and
     *  NAME must not be null, and NAME must be a valid name for a table. */
    public void put(String name, Table table) {
        if (name == null || table == null) {
            throw new IllegalArgumentException("null argument");
        }
        _database.put(name, table);
    }

    /** hashmap for database. */
    private HashMap<String, Table> _database = new HashMap<String, Table>();
}
