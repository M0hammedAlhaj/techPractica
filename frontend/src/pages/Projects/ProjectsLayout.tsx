import { Outlet } from "react-router-dom";

const ProjectsLayout = () => {
  return (
    <div className="min-h-screen">
      <Outlet />
    </div>
  );
};

export default ProjectsLayout;
