import React from "react";
import { TextField, Button, Grid, Paper } from "@material-ui/core";

class AddTodo extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      title: "",
      content: "",
      done: false,
      priority: 0,
      deadline: ""
    };
    this.add = props.add;
  }

  onInputChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  onCheckboxChange = (e) => {
    this.setState({ [e.target.name]: e.target.checked });
  };

  onButtonClick = () => {
    this.add(this.state);
    this.setState({
      title: "",
      content: "",
      done: false,
      priority: 0,
      deadline: ""
    });
  };

  enterKeyEventHandler = (e) => {
    if (e.key === "Enter") {
      this.onButtonClick();
    }
  };

  render() {
    return (
      <Paper style={{ margin: 16, padding: 16 }}>
        <Grid container spacing={2} alignItems="center">
          <Grid item xs={12} md={6}>
            <TextField
              name="title"
              label="Title"
              placeholder="Add Todo Title"
              fullWidth
              onChange={this.onInputChange}
              value={this.state.title}
            />
          </Grid>
          <Grid item xs={12} md={6}>
            <TextField
              name="content"
              label="Content"
              placeholder="Add Todo Content"
              fullWidth
              onChange={this.onInputChange}
              value={this.state.content}
            />
          </Grid>
          <Grid item xs={12} md={3}>
            <TextField
              name="priority"
              label="Priority"
              type="number"
              fullWidth
              onChange={this.onInputChange}
              value={this.state.priority}
            />
          </Grid>
          <Grid item xs={12} md={3}>
            <TextField
              name="deadline"
              label="Deadline"
              type="datetime-local"
              fullWidth
              InputLabelProps={{
                shrink: true,
              }}
              onChange={this.onInputChange}
              value={this.state.deadline}
            />
          </Grid>
          <Grid item xs={12} md={3}>
            <label>
              Done:
              <input
                name="done"
                type="checkbox"
                checked={this.state.done}
                onChange={this.onCheckboxChange}
              />
            </label>
          </Grid>
          <Grid item xs={12} md={3}>
            <Button
              fullWidth
              color="secondary"
              variant="outlined"
              onClick={this.onButtonClick}
            >
              Add Todo
            </Button>
          </Grid>
        </Grid>
      </Paper>
    );
  }
}

export default AddTodo;
