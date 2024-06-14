import React, { useEffect, useState } from "react";
import Todo from "./Todo";
import AddTodo from "./AddTodo";
import {
  Paper,
  List,
  Container,
  Typography,
  AppBar,
  Toolbar,
  Grid,
  Button,
} from "@material-ui/core";
import "./App.css";
import { call } from "./service/ApiService";
import { signout } from "./service/ApiService";
import Alert from "./Alert";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      items: [],
      loading: true,
    };
  }

  add = (item) => {
    call("/todo", "POST", item).then((response) => {
      console.log(response);
      this.setState({
        items: [...this.state.items, response], // 새롭게 생성된 TodoDTO를 현재 상태의 items에 추가
      });
    });
  };

  delete = (todoId) => {
    const updatedItems = this.state.items.filter((todo) => todo.todoId !== todoId);
    this.setState({ items: updatedItems });
  
    call(`/todo?todoId=${todoId}`, "DELETE", null)
      .then((response) => {
        console.log("DELETE response:", response);
      })
      .catch((error) => {
        console.error("DELETE error:", error);
        this.setState({ items: this.state.items });
      });
  };
  
  update = (item) => {
    const todoModificationReq = {
      todoId: item.todoId,
      title: item.title,
      content: item.content,
      done: item.done,
      priority: item.priority,
      deadline: item.deadline,
    };

    call("/todo", "PATCH", todoModificationReq).then((response) =>
      this.setState({ items: response })
    );
  };

  filterByPriority = () => {
    call("/todo/priority", "GET", null).then((response) =>
      this.setState({
        items: response,
      })
    );
  };

  filterByDeadline = () => {
    call("/todo/deadline", "GET", null).then((response) =>
      this.setState({
        items: response,
      })
    );
  };

  componentDidMount() {
    call("/todo", "GET", null).then((response) => {
      console.log("/todo GET: ", response);
      this.setState({ items: response, loading: false }, () => {});
    });
    console.log("State updated:", this.state);
  }

  render() {
    const { items, loading } = this.state;

    const todoItems =
      items && items.length > 0 ? (
        <Paper style={{ margin: 16 }}>
          <Button color="inherit" onClick={this.filterByPriority}>
            우선순위별 정렬
          </Button>
          <Button color="inherit" onClick={this.filterByDeadline}>
            마감 기한별 정렬
          </Button>
          <List>
            {items.map((item, idx) => (
              <Todo
                item={item}
                key={item.todoId}
                deleteTodo={this.delete}
                update={this.update}
              />
            ))}
          </List>
        </Paper>
      ) : (
        <Paper style={{ padding: 16, marginBottom: 16 }}>
          <p>아직 등록된 Todo가 없습니다.</p>
        </Paper>
      );

    // navigationBar
    var navigationBar = (
      <AppBar position="static">
        <Toolbar>
          <Grid container justifyContent="space-between">
            <Grid item>
              <Typography variant="h6">오늘의 할일</Typography>
            </Grid>
          </Grid>
          <Grid item>
            <Button color="inherit" onClick={signout}>
              logout
            </Button>
          </Grid>
        </Toolbar>
      </AppBar>
    );

    // loading 중이 아닐 때
    var todoListPage = (
      <div>
        {navigationBar}
        <Alert />
        <Container maxWidth="md">
          <AddTodo add={this.add} />
          <div className="TodoList">{todoItems}</div>
        </Container>
      </div>
    );

    // loading 중일 때
    var loadingPage = <h1>로딩 중..</h1>;
    var content = loadingPage;

    if (!this.state.loading) {
      content = todoListPage;
    }

    return <div className="App">{content}</div>;
  }
}

export default App;
