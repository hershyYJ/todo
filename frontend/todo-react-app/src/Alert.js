import { Snackbar } from "@material-ui/core";
import { useEffect } from "react";
import { useState } from "react";

const Alert = () => {
  const [alerts, setAlerts] = useState([]);

  useEffect(() => {
    const eventSource = new EventSource("http://localhost:8080/notify");

    eventSource.onmessage = function (event) {
      console.log(alerts);

      const newAlerts = JSON.parse(event.data);
      console.log("/notify GET: " + newAlerts);

      if (newAlerts !== null) {
        setAlerts(newAlerts);
        setTimeout(() => {
          setAlerts([]); // 5초 뒤 알림 제거
        }, 5000);
      }
    };

    eventSource.onerror = function (err) {
      console.error("EventSource failed: ", err);
    };

    return () => {
      eventSource.close();
    };
  }, []);

  return (
    <div className="alert-bar">
      {alerts.map((alert, index) => (
        <Snackbar
          key={index}
          open={open}
          anchorOrigin={{ vertical: "top", horizontal: "center" }}
          message={
            <div>
              <strong>🚨 {alert.username}님, </strong>
              <strong>{alert.title} 할 일이 1시간 뒤에 마감됩니다.</strong>
            </div>
          }
        />
      ))}
    </div>
  );
};

export default Alert;
