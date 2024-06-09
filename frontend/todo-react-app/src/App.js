import React from "react";
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

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      items: [],
      loading: true,
    };
  }

  add = (item) => {
    call("/todo", "POST", item).then((response) =>
      this.setState({
        items: [...this.state.items, response.data], // 새롭게 생성된 TodoDTO를 현재 상태의 items에 추가
      })
    );
  };

  delete = (item) => {
    call(`/todo?todoId=${item.id}`, "DELETE", null).then((response) =>
      this.setState({
        items: this.state.items.filter((todo) => todo.id !== item.id), // 현자 상태의 items에서 삭제한 item을 제거
      })
    );
  };

  update = (item) => {
    call("/todo", "PUT", item).then((response) =>
      this.setState({ items: response.data })
    );
  };

  componentDidMount() {
    console.log("Hello");
    call("/todo", "GET", null).then((response) => {
      console.log(response);
      this.setState({ items: response.data, loading: false });
    });
  }

  render() {
    var todoItems = this.state.items.length > 0 && (
      <Paper style={{ margin: 16 }}>
        <List>
          {this.state.items.map((item, idx) => (
            <Todo
              item={item}
              key={item.id}
              delete={this.delete}
              update={this.update}
            />
          ))}
        </List>
      </Paper>
    );

    // navigationBar
    var navigationBar = (
      <AppBar position="static">
        <Toolbar>
          <Grid justify="space-between" container>
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
