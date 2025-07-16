import { Outlet } from "react-router-dom";
import NavigationBar from "./Sidebar";

export default function Layout() {
  return (
    <>
      <div className="bg-gray-100">
        <NavigationBar /> <Outlet />
      </div>
    </>
  );
}
