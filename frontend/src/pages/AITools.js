import React, { useState } from 'react';
import {
  Box,
  Typography,
  Tabs,
  Tab,
  Paper,
  TextField,
  Button,
  Alert,
  CircularProgress,
  Grid,
  Card,
  CardContent,
  MenuItem,
} from '@mui/material';
import {
  Quiz as QuizIcon,
  TrendingUp as TrendingUpIcon,
  Description as DescriptionIcon,
} from '@mui/icons-material';
import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const AITools = () => {
  const [tabValue, setTabValue] = useState(0);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [response, setResponse] = useState('');

  // Interview Questions State
  const [interviewData, setInterviewData] = useState({
    companyName: '',
    role: '',
  });

  // Next Step Advisor State
  const [advisorData, setAdvisorData] = useState({
    companyName: '',
    role: '',
    applicationStatus: 'APPLIED',
  });

  // Resume Feedback State
  const [resumeData, setResumeData] = useState({
    resumeText: '',
    targetRole: '',
  });

  const handleTabChange = (event, newValue) => {
    setTabValue(newValue);
    setResponse('');
    setError('');
  };

  const handleInterviewQuestions = async () => {
    if (!interviewData.companyName || !interviewData.role) {
      setError('Please fill in all fields');
      return;
    }

    setLoading(true);
    setError('');
    setResponse('');

    try {
      const result = await axios.post(`${API_URL}/ai/interview-questions`, {
        companyName: interviewData.companyName,
        role: interviewData.role,
      });
      setResponse(result.data.response);
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to generate interview questions');
    } finally {
      setLoading(false);
    }
  };

  const handleNextStepAdvice = async () => {
    if (!advisorData.companyName || !advisorData.role) {
      setError('Please fill in all fields');
      return;
    }

    setLoading(true);
    setError('');
    setResponse('');

    try {
      const result = await axios.post(`${API_URL}/ai/next-step-advice`, {
        companyName: advisorData.companyName,
        role: advisorData.role,
        applicationStatus: advisorData.applicationStatus,
      });
      setResponse(result.data.response);
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to generate advice');
    } finally {
      setLoading(false);
    }
  };

  const handleResumeFeedback = async () => {
    if (!resumeData.resumeText || !resumeData.targetRole) {
      setError('Please fill in all fields');
      return;
    }

    setLoading(true);
    setError('');
    setResponse('');

    try {
      const result = await axios.post(`${API_URL}/ai/resume-feedback`, {
        input: resumeData.resumeText,
        role: resumeData.targetRole,
      });
      setResponse(result.data.response);
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to generate resume feedback');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        AI Tools
      </Typography>
      <Typography variant="body1" color="text.secondary" gutterBottom>
        Get intelligent, context-aware guidance for your placement journey
      </Typography>

      <Paper sx={{ mt: 3 }}>
        <Tabs value={tabValue} onChange={handleTabChange} variant="fullWidth">
          <Tab icon={<QuizIcon />} label="Interview Questions" iconPosition="start" />
          <Tab icon={<TrendingUpIcon />} label="Next Step Advisor" iconPosition="start" />
          <Tab icon={<DescriptionIcon />} label="Resume Feedback" iconPosition="start" />
        </Tabs>

        <Box sx={{ p: 3 }}>
          {tabValue === 0 && (
            <Grid container spacing={3}>
              <Grid item xs={12} md={6}>
                <Card>
                  <CardContent>
                    <Typography variant="h6" gutterBottom>
                      Interview Question Generator
                    </Typography>
                    <Typography variant="body2" color="text.secondary" gutterBottom>
                      Get role-specific technical and behavioral questions tailored to your target company and position.
                    </Typography>
                    <TextField
                      fullWidth
                      label="Company Name"
                      value={interviewData.companyName}
                      onChange={(e) =>
                        setInterviewData({ ...interviewData, companyName: e.target.value })
                      }
                      margin="normal"
                    />
                    <TextField
                      fullWidth
                      label="Role"
                      value={interviewData.role}
                      onChange={(e) =>
                        setInterviewData({ ...interviewData, role: e.target.value })
                      }
                      margin="normal"
                    />
                    <Button
                      variant="contained"
                      fullWidth
                      onClick={handleInterviewQuestions}
                      disabled={loading}
                      sx={{ mt: 2 }}
                    >
                      {loading ? <CircularProgress size={24} /> : 'Generate Questions'}
                    </Button>
                  </CardContent>
                </Card>
              </Grid>
              <Grid item xs={12} md={6}>
                {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
                {response && (
                  <Card>
                    <CardContent>
                      <Typography variant="h6" gutterBottom>
                        Generated Questions
                      </Typography>
                      <Typography
                        variant="body2"
                        component="pre"
                        sx={{
                          whiteSpace: 'pre-wrap',
                          fontFamily: 'inherit',
                          maxHeight: '500px',
                          overflow: 'auto',
                        }}
                      >
                        {response}
                      </Typography>
                    </CardContent>
                  </Card>
                )}
              </Grid>
            </Grid>
          )}

          {tabValue === 1 && (
            <Grid container spacing={3}>
              <Grid item xs={12} md={6}>
                <Card>
                  <CardContent>
                    <Typography variant="h6" gutterBottom>
                      Next Step Advisor
                    </Typography>
                    <Typography variant="body2" color="text.secondary" gutterBottom>
                      Get personalized preparation advice based on your current application status and role.
                    </Typography>
                    <TextField
                      fullWidth
                      label="Company Name"
                      value={advisorData.companyName}
                      onChange={(e) =>
                        setAdvisorData({ ...advisorData, companyName: e.target.value })
                      }
                      margin="normal"
                    />
                    <TextField
                      fullWidth
                      label="Role"
                      value={advisorData.role}
                      onChange={(e) =>
                        setAdvisorData({ ...advisorData, role: e.target.value })
                      }
                      margin="normal"
                    />
                    <TextField
                      fullWidth
                      select
                      label="Application Status"
                      value={advisorData.applicationStatus}
                      onChange={(e) =>
                        setAdvisorData({ ...advisorData, applicationStatus: e.target.value })
                      }
                      margin="normal"
                    >
                      <MenuItem value="APPLIED">Applied</MenuItem>
                      <MenuItem value="ONLINE_ASSESSMENT">Online Assessment</MenuItem>
                      <MenuItem value="INTERVIEW">Interview</MenuItem>
                      <MenuItem value="OFFER">Offer</MenuItem>
                      <MenuItem value="REJECTED">Rejected</MenuItem>
                    </TextField>
                    <Button
                      variant="contained"
                      fullWidth
                      onClick={handleNextStepAdvice}
                      disabled={loading}
                      sx={{ mt: 2 }}
                    >
                      {loading ? <CircularProgress size={24} /> : 'Get Advice'}
                    </Button>
                  </CardContent>
                </Card>
              </Grid>
              <Grid item xs={12} md={6}>
                {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
                {response && (
                  <Card>
                    <CardContent>
                      <Typography variant="h6" gutterBottom>
                        Preparation Advice
                      </Typography>
                      <Typography
                        variant="body2"
                        component="pre"
                        sx={{
                          whiteSpace: 'pre-wrap',
                          fontFamily: 'inherit',
                          maxHeight: '500px',
                          overflow: 'auto',
                        }}
                      >
                        {response}
                      </Typography>
                    </CardContent>
                  </Card>
                )}
              </Grid>
            </Grid>
          )}

          {tabValue === 2 && (
            <Grid container spacing={3}>
              <Grid item xs={12} md={6}>
                <Card>
                  <CardContent>
                    <Typography variant="h6" gutterBottom>
                      Resume Feedback Assistant
                    </Typography>
                    <Typography variant="body2" color="text.secondary" gutterBottom>
                      Get AI-powered feedback on your resume tailored to your target role.
                    </Typography>
                    <TextField
                      fullWidth
                      label="Target Role"
                      value={resumeData.targetRole}
                      onChange={(e) =>
                        setResumeData({ ...resumeData, targetRole: e.target.value })
                      }
                      margin="normal"
                    />
                    <TextField
                      fullWidth
                      label="Resume Text"
                      value={resumeData.resumeText}
                      onChange={(e) =>
                        setResumeData({ ...resumeData, resumeText: e.target.value })
                      }
                      margin="normal"
                      multiline
                      rows={10}
                      placeholder="Paste your resume content here..."
                    />
                    <Button
                      variant="contained"
                      fullWidth
                      onClick={handleResumeFeedback}
                      disabled={loading}
                      sx={{ mt: 2 }}
                    >
                      {loading ? <CircularProgress size={24} /> : 'Get Feedback'}
                    </Button>
                  </CardContent>
                </Card>
              </Grid>
              <Grid item xs={12} md={6}>
                {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
                {response && (
                  <Card>
                    <CardContent>
                      <Typography variant="h6" gutterBottom>
                        Resume Feedback
                      </Typography>
                      <Typography
                        variant="body2"
                        component="pre"
                        sx={{
                          whiteSpace: 'pre-wrap',
                          fontFamily: 'inherit',
                          maxHeight: '500px',
                          overflow: 'auto',
                        }}
                      >
                        {response}
                      </Typography>
                    </CardContent>
                  </Card>
                )}
              </Grid>
            </Grid>
          )}
        </Box>
      </Paper>
    </Box>
  );
};

export default AITools;
