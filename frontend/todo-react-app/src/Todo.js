import React from "react";
import {
  ListItem,
  ListItemText,
  InputBase,
  Checkbox,
  ListItemSecondaryAction,
  IconButton,
} from "@material-ui/core";
import DeleteOutlined from "@material-ui/icons/DeleteOutlined";

class Todo extends React.Component {
  constructor(props) {
    super(props);
    this.state = { item: props.item, readOnly: true };
    this.delete = props.delete;
    this.update = props.update;
  }

  deleteEventHandler = () => {
    this.delete(this.state.item);
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
    const thisItem = this.state.item;
    thisItem.title = e.target.value;
    this.setState({ item: thisItem });
  };

  checkboxHandler = (e) => {
    const thisItem = this.state.item;
    thisItem.done = thisItem.done ? false : true;
    this.setState({ readOnly: true });
    this.update(this.state.item);
  };

  render() {
    const item = this.state.item;
    return (
      <ListItem>
        <Checkbox checked={item.done} onChange={this.checkboxEventHandler} />
        <ListItemText>
          <InputBase
            inputProps={{
              "aria-label": "naked",
              readOnly: this.state.readOnly,
            }}
            type="text"
            id={item.id}
            name={item.id}
            value={item.title}
            multiline={true}
            fullWidth={true}
            onClick={this.offReadOnlyMode}
            onChange={this.editEventHandler}
            onKeyDown={this.enterKeyEventHandler}
          />
        </ListItemText>

        <ListItemSecondaryAction>
          <IconButton aria label="Delete" onClick={this.deleteEventHandler}>
            <DeleteOutlined />
          </IconButton>
        </ListItemSecondaryAction>
      </ListItem>
    );
  }
}

export default Todo;
