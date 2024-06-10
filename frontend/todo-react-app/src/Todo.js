import React from "react";
import { ListItem, ListItemText, ListItemSecondaryAction, IconButton, Checkbox, InputBase } from "@material-ui/core";
import DeleteOutlined from "@material-ui/icons/DeleteOutlined";

class Todo extends React.Component {
  constructor(props) {
    super(props);
    this.state = { item: props.item, readOnly: true };
    this.deleteTodo = props.deleteTodo;
    this.update = props.update;
  }

  componentDidMount() {
    console.log('Todo.Item:', this.props.item);
  }  

  deleteEventHandler = () => {
    const { item } = this.state;
    console.log("Item:", item);
    this.props.deleteTodo(item.todoId);
  };

  offReadOnlyMode = () => {
    this.setState({ readOnly: false }, () => {
      console.log("ReadOnly?", this.state.readOnly);
    });
  };

  enterKeyEventHandler = (e) => {
    if (e.key === "Enter") {
      this.setState({ readOnly: true });
      this.update(this.state.item);
    }
  };

  editEventHandler = (e) => {
    const { name, value } = e.target;
    const updatedItem = { ...this.state.item, [name]: value };
    this.setState({ item: updatedItem });
  };

  checkboxEventHandler = () => {
    const { item } = this.state;
    const updatedItem = { ...item, done: !item.done };
    this.setState({ readOnly: true, item: updatedItem }, () => {
      this.update(updatedItem);
    });
  };

  render() {
    const { item, readOnly } = this.state;

    return (
      <ListItem>
        <Checkbox
          checked={item.done}
          onChange={this.checkboxEventHandler}
          disabled={readOnly}
        />
        <ListItemText>
          <label htmlFor={`${item.todoId}-title`}>제목:</label>
          <InputBase
            inputProps={{
              "aria-label": "title",
              readOnly: readOnly,
            }}
            type="text"
            id={`${item.todoId}-title`}
            name="title"
            value={item.title}
            fullWidth={true}
            multiline={false}
            onClick={this.offReadOnlyMode}
            onChange={this.editEventHandler}
            onKeyDown={this.enterKeyEventHandler}
          />

          <label htmlFor={`${item.todoId}-content`}>내용:</label>
          <InputBase
            inputProps={{
              "aria-label": "content",
              readOnly: readOnly,
            }}
            type="text"
            id={`${item.todoId}-content`}
            name="content"
            value={item.content}
            fullWidth={true}
            multiline={false}
            onClick={this.offReadOnlyMode}
            onChange={this.editEventHandler}
            onKeyDown={this.enterKeyEventHandler}
          />

          <label htmlFor={`${item.todoId}-priority`}>우선 순위:</label>
          <InputBase
            inputProps={{
              "aria-label": "priority",
              readOnly: readOnly,
            }}
            type="number"
            id={`${item.todoId}-priority`}
            name="priority"
            value={item.priority}
            fullWidth={true}
            multiline={false}
            onClick={this.offReadOnlyMode}
            onChange={this.editEventHandler}
            onKeyDown={this.enterKeyEventHandler}
          />

          <label htmlFor={`${item.todoId}-deadline`}>마감 시간:</label>
          <InputBase
            inputProps={{
              "aria-label": "deadline",
              readOnly: readOnly,
            }}
            type="datetime-local"
            id={`${item.todoId}-deadline`}
            name="deadline"
            value={item.deadline}
            fullWidth={true}
            multiline={false}
            onClick={this.offReadOnlyMode}
            onChange={this.editEventHandler}
            onKeyDown={this.enterKeyEventHandler}
          />
        </ListItemText>

        <ListItemSecondaryAction>
          <IconButton aria-label="Delete" onClick={this.deleteEventHandler}>
            <DeleteOutlined />
          </IconButton>
        </ListItemSecondaryAction>
      </ListItem>
    );
  }
}

export default Todo;