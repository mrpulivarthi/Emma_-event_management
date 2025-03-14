package com.event.dao;

import com.event.models.EventType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventTypeDAO {

    // Create a new event type
    public int createEventType(EventType eventType) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO event_types (type_name) VALUES (?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, eventType.getTypeName());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating event type failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Returns the generated event type ID
                } else {
                    throw new SQLException("Creating event type failed, no ID obtained.");
                }
            }
        }
    }

    // Get all event types
    public List<EventType> getAllEventTypes() throws SQLException, ClassNotFoundException {
        List<EventType> eventTypes = new ArrayList<>();
        String sql = "SELECT * FROM event_types ORDER BY type_name";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                EventType eventType = new EventType();
                eventType.setTypeId(rs.getInt("type_id"));
                eventType.setTypeName(rs.getString("type_name"));
                
                eventTypes.add(eventType);
            }
        }
        
        return eventTypes;
    }

    // Get event type by ID
    public EventType getEventTypeById(int typeId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM event_types WHERE type_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, typeId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    EventType eventType = new EventType();
                    eventType.setTypeId(rs.getInt("type_id"));
                    eventType.setTypeName(rs.getString("type_name"));
                    
                    return eventType;
                }
            }
        }
        
        return null; // Return null if event type not found
    }

    // Update event type
    public boolean updateEventType(EventType eventType) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE event_types SET type_name = ? WHERE type_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, eventType.getTypeName());
            stmt.setInt(2, eventType.getTypeId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0; // Return true if update was successful
        }
    }

    // Delete event type
    public boolean deleteEventType(int typeId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM event_types WHERE type_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, typeId);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0; // Return true if delete was successful
        }
    }
}

