package wtf.cmyk.toomanycolors.storage;

import wtf.cmyk.toomanycolors.TMC;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

public class SQLiteProvider extends StorageProvider {
    private Connection connection;
    private TMC plugin;

    @Override
    public void init() {
        plugin = TMC.getInstance();
        connection = getConnection();
        initializeTable();
    }
    
    private Connection getConnection() {
        File dbFile;
        // Used for testing
        if(plugin != null) {
            dbFile = new File(plugin.getDataFolder(), "data.db");
        } else {
            dbFile = new File("data.db");
        }

        if(!dbFile.exists()) {
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                if(plugin != null) {
                    plugin.getLogger().severe("Shutting down, Error creating database file. Clear any existing .db files from the " +
                        "plugin folder, Contact developer if this issue persists.");
                    plugin.getPluginLoader().disablePlugin(plugin);
                } else {
                    e.printStackTrace();
                }
            }
        }
        try {
            if(connection != null && !connection.isClosed()) return connection;
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        } catch (SQLException e) {
            if(plugin != null) {
                plugin.getLogger().severe("Shutting down, Error initializing sqlite connection Clear any existing .db files from the " +
                    "plugin folder, Contact developer if this issue persists.");
                plugin.getPluginLoader().disablePlugin(plugin);
            } else {
                e.printStackTrace();
            }
        }
        catch (ClassNotFoundException e) {
            if(plugin != null) {
                plugin.getLogger().severe("Shutting down, Missing SQLite JDBC library. Contact plugin developer.");
                plugin.getPluginLoader().disablePlugin(plugin);
            } else {
                e.printStackTrace();
            }
        }
        return connection;
    }

    private void initializeTable() {
        String query = "CREATE TABLE IF NOT EXISTS tmc_data (" +
                "player varchar(32) NOT NULL, " +
                "placeholder varchar NOT NULL," +
                "hex_code varchar(6) NOT NULL)";
        try {
            Statement s = getConnection().createStatement();
            s.executeUpdate(query);
            s.close();
        } catch (SQLException e) {
            if(plugin != null) {
                plugin.getLogger().severe("Shutting down, Error initializing table. Clear any existing .db files from the " +
                        "plugin folder, Contact developer if this issue persists.");
                plugin.getPluginLoader().disablePlugin(plugin);
            } else {
                e.printStackTrace();
            }
        }
    }

    public void createPlaceholder(String uuid, String placeholder, String hexColor) {
        String query = "INSERT INTO tmc_data (player, placeholder, hex_code) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setString(1, uuid);
            ps.setString(2, placeholder);
            ps.setString(3, hexColor);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            if (plugin != null) {
                plugin.getLogger().warning("Error creating placeholder for " + uuid);
            } else {
                e.printStackTrace();
            }
        }
    }


    public void updatePlaceholder(String uuid, String placeholder, String hexColor) {
        String query = "UPDATE tmc_data SET hex_code = ? WHERE player = ? AND placeholder = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setString(1, hexColor);
            ps.setString(2, uuid);
            ps.setString(3, placeholder);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            if (plugin != null) {
                plugin.getLogger().warning("Error updating placeholder for " + uuid);
            } else {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setPlaceholder(String uuid, String placeholder, String hexColor) {
        if(hasPlaceholder(uuid, placeholder)) {
            updatePlaceholder(uuid, placeholder, hexColor);
        } else {
            createPlaceholder(uuid, placeholder, hexColor);
        }
    }

    @Override
    public void delPlaceholder(String uuid, String placeholder) {
        String query = "DELETE from tmc_data WHERE player=? AND placeholder=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setString(1, uuid);
            ps.setString(2, placeholder);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            if (plugin != null) {
                plugin.getLogger().warning("Error deleting placeholder for " + uuid);
            } else {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Integer getTotalPlaceholders(String uuid) {
        String query = "SELECT COUNT(1) FROM tmc_data WHERE player=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
            ps.close();
        } catch (SQLException e) {
            if(plugin != null) {
                plugin.getLogger().warning("Error getting total placeholders for " + uuid);
            } else {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public HashMap<String, String> getAllPlaceholders(String uuid) {
        HashMap<String,String> retVal = new HashMap<>();
        String query = "SELECT * FROM tmc_data WHERE player=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                retVal.put(rs.getString("placeholder"), rs.getString("hex_code"));
            }
            ps.close();
        } catch (SQLException e) {
            if(plugin != null) {
                plugin.getLogger().warning("Error getting color replacement for " + uuid);
            } else {
                e.printStackTrace();
            }
        }
        return retVal;
    }

    @Override
    public Boolean hasPlaceholder(String uuid, String placeholder) {
        String query = "SELECT COUNT(1) FROM tmc_data WHERE player=? AND placeholder=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setString(1, uuid);
            ps.setString(2, placeholder);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1) == 1;
            }
            ps.close();
        } catch (SQLException e) {
            if(plugin != null) {
                plugin.getLogger().warning("Error getting color replacement for " + uuid);
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public String getHexColor(String uuid, String placeholder) {
        String query = "SELECT hex_code FROM tmc_data WHERE player=? AND placeholder=?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            ps.setString(1, uuid);
            ps.setString(2, placeholder);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getString(1);
            }
            ps.close();
        } catch (SQLException e) {
            if(plugin != null) {
                plugin.getLogger().warning("Error getting color replacement for " + uuid);
            } else {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void close() {
        try {
            if(connection != null) getConnection().close();
        } catch (SQLException e) {
            if(plugin != null) {
                plugin.getLogger().severe("Error closing connection to database file.");
            } else {
                e.printStackTrace();
            }
        }
    }
}
