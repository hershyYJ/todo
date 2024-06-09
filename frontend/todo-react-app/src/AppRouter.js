import { Box, Typography } from "@material-ui/core";

import SignUp from "./service/SignUp";
import Login from "./service/Login.js";
import App from "./App";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import React from "react";

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {"CopyRight "}
      fsoftwareengineer, {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

class AppRouter extends React.Component {
  render() {
    return (
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/" element={<App />} />
        </Routes>
      </BrowserRouter>
    );
  }
}

export default AppRouter;
