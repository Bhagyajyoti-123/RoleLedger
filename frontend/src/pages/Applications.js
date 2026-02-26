import React, { useState, useEffect } from 'react';
import {
  Box,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
  MenuItem,
  Alert,
  CircularProgress,
  Chip,
} from '@mui/material';
import {
  Add as AddIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
} from '@mui/icons-material';
import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const statusColors = {
  APPLIED: 'primary',
  ONLINE_ASSESSMENT: 'warning',
  INTERVIEW: 'secondary',
  OFFER: 'success',
  REJECTED: 'error',
};

const Applications = () => {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [formData, setFormData] = useState({
    companyName: '',
    role: '',
    status: 'APPLIED',
    dateApplied: new Date().toISOString().split('T')[0],
    notes: '',
  });

  useEffect(() => {
    fetchApplications();
  }, []);

  const fetchApplications = async () => {
    try {
      const response = await axios.get(`${API_URL}/applications`);
      setApplications(response.data);
      setLoading(false);
    } catch (err) {
      setError('Failed to load applications');
      setLoading(false);
    }
  };

  const handleOpen = () => {
    setEditing(null);
    setFormData({
      companyName: '',
      role: '',
      status: 'APPLIED',
      dateApplied: new Date().toISOString().split('T')[0],
      notes: '',
    });
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setEditing(null);
  };

  const handleEdit = (application) => {
    setEditing(application);
    setFormData({
      companyName: application.companyName,
      role: application.role,
      status: application.status,
      dateApplied: application.dateApplied,
      notes: application.notes || '',
    });
    setOpen(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this application?')) {
      try {
        await axios.delete(`${API_URL}/applications/${id}`);
        fetchApplications();
      } catch (err) {
        setError('Failed to delete application');
      }
    }
  };

  const handleSubmit = async () => {
    try {
      if (editing) {
        await axios.put(`${API_URL}/applications/${editing.id}`, formData);
      } else {
        await axios.post(`${API_URL}/applications`, formData);
      }
      handleClose();
      fetchApplications();
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to save application');
    }
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4">Applications</Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={handleOpen}
        >
          Add Application
        </Button>
      </Box>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }} onClose={() => setError('')}>
          {error}
        </Alert>
      )}

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Company</TableCell>
              <TableCell>Role</TableCell>
              <TableCell>Status</TableCell>
              <TableCell>Date Applied</TableCell>
              <TableCell>Notes</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {applications.length === 0 ? (
              <TableRow>
                <TableCell colSpan={6} align="center">
                  <Typography color="textSecondary">
                    No applications yet. Click "Add Application" to get started.
                  </Typography>
                </TableCell>
              </TableRow>
            ) : (
              applications.map((app) => (
                <TableRow key={app.id}>
                  <TableCell>{app.companyName}</TableCell>
                  <TableCell>{app.role}</TableCell>
                  <TableCell>
                    <Chip
                      label={app.status.replace('_', ' ')}
                      color={statusColors[app.status]}
                      size="small"
                    />
                  </TableCell>
                  <TableCell>{app.dateApplied}</TableCell>
                  <TableCell>
                    {app.notes ? (
                      <Typography variant="body2" noWrap sx={{ maxWidth: 200 }}>
                        {app.notes}
                      </Typography>
                    ) : (
                      '-'
                    )}
                  </TableCell>
                  <TableCell>
                    <IconButton
                      size="small"
                      onClick={() => handleEdit(app)}
                      color="primary"
                    >
                      <EditIcon />
                    </IconButton>
                    <IconButton
                      size="small"
                      onClick={() => handleDelete(app.id)}
                      color="error"
                    >
                      <DeleteIcon />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))
            )}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
        <DialogTitle>
          {editing ? 'Edit Application' : 'Add New Application'}
        </DialogTitle>
        <DialogContent>
          <Box sx={{ pt: 2 }}>
            <TextField
              fullWidth
              label="Company Name"
              value={formData.companyName}
              onChange={(e) =>
                setFormData({ ...formData, companyName: e.target.value })
              }
              margin="normal"
              required
            />
            <TextField
              fullWidth
              label="Role"
              value={formData.role}
              onChange={(e) =>
                setFormData({ ...formData, role: e.target.value })
              }
              margin="normal"
              required
            />
            <TextField
              fullWidth
              select
              label="Status"
              value={formData.status}
              onChange={(e) =>
                setFormData({ ...formData, status: e.target.value })
              }
              margin="normal"
              required
            >
              <MenuItem value="APPLIED">Applied</MenuItem>
              <MenuItem value="ONLINE_ASSESSMENT">Online Assessment</MenuItem>
              <MenuItem value="INTERVIEW">Interview</MenuItem>
              <MenuItem value="OFFER">Offer</MenuItem>
              <MenuItem value="REJECTED">Rejected</MenuItem>
            </TextField>
            <TextField
              fullWidth
              type="date"
              label="Date Applied"
              value={formData.dateApplied}
              onChange={(e) =>
                setFormData({ ...formData, dateApplied: e.target.value })
              }
              margin="normal"
              InputLabelProps={{
                shrink: true,
              }}
              required
            />
            <TextField
              fullWidth
              label="Notes"
              value={formData.notes}
              onChange={(e) =>
                setFormData({ ...formData, notes: e.target.value })
              }
              margin="normal"
              multiline
              rows={4}
            />
          </Box>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleSubmit} variant="contained">
            {editing ? 'Update' : 'Create'}
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default Applications;
