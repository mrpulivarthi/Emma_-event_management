package com.event.dao;

import com.event.models.RSVP;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RSVPDAO {

    // Create a new RSVP
    public int createRSVP(RSVP rsvp) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO rsvp (event_id, user_name, email, attendance_status) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, rsvp.getEventId());
            stmt.setString(2, rsvp.getUserName());
            stmt.setString(3, rsvp.getEmail());
            stmt.setString(4, rsvp.getAttendanceStatus());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating RSVP failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Returns the generated RSVP ID
                } else {
                    throw new SQLException("Creating RSVP failed, no ID obtained.");
                }
            }
        }
    }

    // Get all RSVPs
    public List<RSVP> getAllRSVPs() throws SQLException, ClassNotFoundException {
        List<RSVP> rsvps = new ArrayList<>();
        String sql = "SELECT * FROM rsvp ORDER BY created_at";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                RSVP rsvp = new RSVP();
                rsvp.setRsvpId(rs.getInt("rsvp_id"));
                rsvp.setEventId(rs.getInt("event_id"));
                rsvp.setUserName(rs.getString("user_name"));
                rsvp.setEmail(rs.getString("email"));
                rsvp.setAttendanceStatus(rs.getString("attendance_status"));
                rsvp.setCreatedAt(rs.getTimestamp("created_at"));
                
                rsvps.add(rsvp);
            }
        }
        
        return rsvps;
    }

    // Get RSVP by ID
    public RSVP getRSVPById(int rsvpId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM rsvp WHERE rsvp_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, rsvpId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    RSVP rsvp = new RSVP();
                    rsvp.setRsvpId(rs.getInt("rsvp_id"));
                    rsvp.setEventId(rs.getInt("event_id"));
                    rsvp.setUserName(rs.getString("user_name"));
                    rsvp.setEmail(rs.getString("email"));
                    rsvp.setAttendanceStatus(rs.getString("attendance_status"));
                    rsvp.setCreatedAt(rs.getTimestamp("created_at"));
                    
                    return rsvp;
                }
            }
        }
        
        return null; // Return null if RSVP not found
    }

    // Update RSVP
    public boolean updateRSVP(RSVP rsvp) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE rsvp SET user_name = ?, email = ?, attendance_status = ? WHERE rsvp_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, rsvp.getUserName());
            stmt.setString(2, rsvp.getEmail());
            stmt.setString(3, rsvp.getAttendanceStatus());
            stmt.setInt(4, rsvp.getRsvpId());
        
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0; // Return true if update was successful
        }
    }

    // Delete RSVP
    public boolean deleteRSVP(int rsvpId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM rsvp WHERE rsvp_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, rsvpId);
        
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0; // Return true if delete was successful
        }
    }
}
