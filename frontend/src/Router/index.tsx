import {
  createBrowserRouter,
  createRoutesFromElements,
  Route,
} from "react-router-dom";
import {
  LayoutHome,
  LayoutLogin,
  Home,
  PageNotFound,
  Login,
  Learn,
  Profile,
  Register,
  ResetPass,
  ProjectsLayout,
  Projects,
  SessionRequests,
} from "../imports";
import BorderLayout from "../components/Board/BorderLayout";
import KanbanBoard from "../components/Board/KanbanBoard";

export const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path="/" element={<LayoutHome />}>
        <Route index element={<Home />} />
        <Route path="Learn" element={<Learn />} />
        <Route path="Learn/:category" element={<Learn />} />
        <Route path="Sessions" element={<ProjectsLayout />}>
          <Route index element={<Projects />} />
        </Route>
        <Route path="/Requests/:id" element={<SessionRequests />} />
        <Route path="Profile" element={<Profile />} />
      </Route>

      <Route path="User" element={<LayoutLogin />}>
        <Route index element={<Login />} />
        <Route path="ResetPassword" element={<ResetPass />} />
        <Route path="Register" element={<Register />} />
      </Route>
      <Route path="SessionKanban" element={<BorderLayout />}>
        <Route index element={<KanbanBoard />} />
      </Route>
      <Route path="*" element={<PageNotFound />} />
    </>
  )
);
