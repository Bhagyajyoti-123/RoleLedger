import React, { useState, useEffect } from 'react';
import {
  Box,
  Grid,
  Card,
  CardContent,
  Typography,
  CircularProgress,
  Alert,
} from '@mui/material';
import {
  Work as WorkIcon,
  CheckCircle as CheckCircleIcon,
  Assessment as AssessmentIcon,
  Cancel as CancelIcon,
  Send as SendIcon,
} from '@mui/icons-material';
import { PieChart, Pie, Cell, ResponsiveContainer, Legend, Tooltip, BarChart, Bar, XAxis, YAxis, CartesianGrid } from 'recharts';
import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const COLORS = {
  APPLIED: '#1976d2',
  ONLINE_ASSESSMENT: '#ed6c02',
  INTERVIEW: '#9c27b0',
  OFFER: '#2e7d32',
  REJECTED: '#d32f2f',
};

const Dashboard = () => {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchStats();
  }, []);

  const fetchStats = async () => {
    try {
      const response = await axios.get(`${API_URL}/applications/dashboard/stats`);
      setStats(response.data);
      setLoading(false);
    } catch (err) {
      setError('Failed to load dashboard statistics');
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return <Alert severity="error">{error}</Alert>;
  }

  const pieData = stats.statusBreakdown
    ? Object.entries(stats.statusBreakdown).map(([status, count]) => ({
        name: status.replace('_', ' '),
        value: count,
      }))
    : [];

  const barData = stats.statusBreakdown
    ? Object.entries(stats.statusBreakdown).map(([status, count]) => ({
        status: status.replace('_', ' '),
        count: count,
      }))
    : [];

  const getStatusIcon = (status) => {
    switch (status) {
      case 'APPLIED':
        return <SendIcon />;
      case 'ONLINE_ASSESSMENT':
        return <AssessmentIcon />;
      case 'INTERVIEW':
        return <AssessmentIcon />;
      case 'OFFER':
        return <CheckCircleIcon />;
      case 'REJECTED':
        return <CancelIcon />;
      default:
        return <WorkIcon />;
    }
  };

  const statusCards = stats.statusBreakdown
    ? Object.entries(stats.statusBreakdown).map(([status, count]) => (
        <Grid item xs={12} sm={6} md={4} key={status}>
          <Card>
            <CardContent>
              <Box display="flex" alignItems="center" justifyContent="space-between">
                <Box>
                  <Typography color="textSecondary" gutterBottom variant="body2">
                    {status.replace('_', ' ')}
                  </Typography>
                  <Typography variant="h4">{count}</Typography>
                </Box>
                <Box sx={{ color: COLORS[status] }}>
                  {getStatusIcon(status)}
                </Box>
              </Box>
            </CardContent>
          </Card>
        </Grid>
      ))
    : [];

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Dashboard
      </Typography>
      <Grid container spacing={3} sx={{ mt: 2 }}>
        <Grid item xs={12}>
          <Card>
            <CardContent>
              <Box display="flex" alignItems="center" justifyContent="space-between">
                <Box>
                  <Typography color="textSecondary" gutterBottom variant="body2">
                    Total Applications
                  </Typography>
                  <Typography variant="h3">{stats?.totalApplications || 0}</Typography>
                </Box>
                <WorkIcon sx={{ fontSize: 60, color: 'primary.main' }} />
              </Box>
            </CardContent>
          </Card>
        </Grid>

        {statusCards}

        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Status Distribution
              </Typography>
              <ResponsiveContainer width="100%" height={300}>
                <PieChart>
                  <Pie
                    data={pieData}
                    cx="50%"
                    cy="50%"
                    labelLine={false}
                    label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}
                    outerRadius={80}
                    fill="#8884d8"
                    dataKey="value"
                  >
                    {pieData.map((entry, index) => {
                      const status = Object.keys(stats.statusBreakdown)[index];
                      return (
                        <Cell key={`cell-${index}`} fill={COLORS[status]} />
                      );
                    })}
                  </Pie>
                  <Tooltip />
                  <Legend />
                </PieChart>
              </ResponsiveContainer>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Applications by Status
              </Typography>
              <ResponsiveContainer width="100%" height={300}>
                <BarChart data={barData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="status" />
                  <YAxis />
                  <Tooltip />
                  <Bar dataKey="count" fill="#1976d2" />
                </BarChart>
              </ResponsiveContainer>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Box>
  );
};

export default Dashboard;
