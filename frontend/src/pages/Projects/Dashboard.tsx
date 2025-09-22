import { useState, useMemo, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import Pagination from "../../components/NewPagination";
import { ProjectCard } from "../../components/Cards/DashboardSessionCard";
import { ProjectModal } from "../../components/NewProjectModel";
import { Filter, FolderOpen, Plus, Search } from "lucide-react";
import { useSystems } from "../../api";

const statuses = ["All", "draft", "in-progress", "completed"];
const visibilities = ["All", "public", "private"];
const sortOptions = [
  { value: "updated", label: "Recently Updated" },
  { value: "created", label: "Recently Created" },
  { value: "name", label: "Name A-Z" },
  { value: "views", label: "Most Viewed" },
  { value: "commits", label: "Most Active" },
];

const ITEMS_PER_PAGE = 6;

// Helper to know when we're on desktop
function useIsDesktop(breakpoint = 1024) {
  const [isDesktop, setIsDesktop] = useState(false);
  useEffect(() => {
    const update = () => setIsDesktop(window.innerWidth >= breakpoint);
    update();
    window.addEventListener("resize", update);
    return () => window.removeEventListener("resize", update);
  }, [breakpoint]);
  return isDesktop;
}

// Create/Edit Project Modal

export default function Dashboard() {
  const [projects, setProjects] = useState(mockUserProjects);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("All");
  const [selectedStatus, setSelectedStatus] = useState("All");
  const [selectedVisibility, setSelectedVisibility] = useState("All");
  const [sortBy, setSortBy] = useState("updated");
  const [currentPage, setCurrentPage] = useState(1);
  const [showFilters, setShowFilters] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingProject, setEditingProject] = useState<any>(null);

  const isDesktop = useIsDesktop();

  // Filter and sort projects
  const filteredAndSortedProjects = useMemo(() => {
    const filtered = projects.filter((project) => {
      const matchesSearch =
        project.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        project.description.toLowerCase().includes(searchTerm.toLowerCase()) ||
        project.technologies.some((tech) =>
          tech.toLowerCase().includes(searchTerm.toLowerCase())
        );

      const matchesCategory =
        selectedCategory === "All" || project.category === selectedCategory;
      const matchesStatus =
        selectedStatus === "All" || project.status === selectedStatus;
      const matchesVisibility =
        selectedVisibility === "All" ||
        project.visibility === selectedVisibility;

      return (
        matchesSearch && matchesCategory && matchesStatus && matchesVisibility
      );
    });

    return filtered;
  }, [
    projects,
    searchTerm,
    selectedCategory,
    selectedStatus,
    selectedVisibility,
    sortBy,
  ]);

  // Pagination
  const totalPages = Math.ceil(
    filteredAndSortedProjects.length / ITEMS_PER_PAGE
  );
  const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
  const paginatedProjects = filteredAndSortedProjects.slice(
    startIndex,
    startIndex + ITEMS_PER_PAGE
  );

  // Handlers
  const handleCreateProject = () => {
    setEditingProject(null);
    setIsModalOpen(true);
  };

  const handleEditProject = (project: any) => {
    setEditingProject(project);
    setIsModalOpen(true);
  };

  const handleSaveProject = (projectData: any) => {
    if (editingProject) {
      setProjects(
        projects.map((p) => (p.id === editingProject.id ? projectData : p))
      );
    } else {
      setProjects([projectData, ...projects]);
    }
  };

  const handleDeleteProject = (id: number) => {
    if (confirm("Are you sure you want to delete this project?")) {
      setProjects(projects.filter((p) => p.id !== id));
    }
  };

  const handleViewProject = (id: number) => {
    console.log("Viewing project:", id);
    // Navigate to project detail page
  };

  const clearFilters = () => {
    setSearchTerm("");
    setSelectedCategory("All");
    setSelectedStatus("All");
    setSelectedVisibility("All");
    setSortBy("updated");
    setCurrentPage(1);
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Hero Section */}
      <section className="bg-gradient-to-r from-[#42D5AE] to-[#022639] py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6 }}
            className="flex flex-col md:flex-row justify-between items-start md:items-center gap-6"
          >
            <div className="text-white">
              <h1 className="text-4xl md:text-5xl font-bold mb-4">
                My Projects
              </h1>
              <p className="text-xl text-[#42D5AE]/80 max-w-2xl">
                Manage, organize, and showcase your development projects
              </p>
            </div>
            <motion.button
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
              onClick={handleCreateProject}
              className="bg-white text-[#022639] hover:bg-gray-50 px-8 py-4 rounded-xl font-bold transition-all duration-300 flex items-center gap-3 shadow-lg"
            >
              <Plus className="w-5 h-5" />
              New Project
            </motion.button>
          </motion.div>
        </div>
      </section>

      {/* Filters and Search */}
      <section className="py-8 bg-white border-b">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex flex-col lg:flex-row gap-4 items-start lg:items-center justify-between">
            {/* Search */}
            <div className="relative flex-1 max-w-md">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
              <input
                type="text"
                placeholder="Search projects..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-[#42D5AE] focus:border-transparent outline-none transition-all"
              />
            </div>

            {/* Filter Toggle */}
            <div className="flex items-center gap-4">
              <button
                onClick={() => setShowFilters(!showFilters)}
                className="lg:hidden flex items-center gap-2 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors"
              >
                <Filter className="h-4 w-4" />
                Filters
              </button>

              {/* Sort */}
              <div className="flex items-center gap-2">
                <span className="text-sm font-medium text-gray-700">Sort:</span>
                <select
                  value={sortBy}
                  onChange={(e) => setSortBy(e.target.value)}
                  className="px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-[#42D5AE] focus:border-transparent outline-none bg-white"
                >
                  {sortOptions.map((option) => (
                    <option key={option.value} value={option.value}>
                      {option.label}
                    </option>
                  ))}
                </select>
              </div>
            </div>
          </div>

          {/* Filters */}
          <AnimatePresence>
            {(showFilters || isDesktop) && (
              <motion.div
                initial={{ opacity: 0, height: 0 }}
                animate={{ opacity: 1, height: "auto" }}
                exit={{ opacity: 0, height: 0 }}
                transition={{ duration: 0.3 }}
                className="mt-6 flex flex-col lg:flex-row gap-4 items-start lg:items-center"
              >
                {/* Category Filter */}
                <div className="flex flex-wrap gap-2">
                  <span className="text-sm font-medium text-gray-700 mr-2">
                    Category:
                  </span>
                  {categories.map((category) => (
                    <button
                      key={category}
                      onClick={() => setSelectedCategory(category)}
                      className={`px-3 py-1 rounded-full text-xs font-medium border transition-colors ${
                        selectedCategory === category
                          ? "bg-[#42D5AE] text-white border-[#42D5AE]"
                          : "bg-white text-gray-700 border-gray-300 hover:bg-[#42D5AE]/10 hover:border-[#42D5AE]/30"
                      }`}
                    >
                      {category}
                    </button>
                  ))}
                </div>

                {/* Status Filter */}
                <div className="flex flex-wrap gap-2">
                  <span className="text-sm font-medium text-gray-700 mr-2">
                    Status:
                  </span>
                  {statuses.map((status) => (
                    <button
                      key={status}
                      onClick={() => setSelectedStatus(status)}
                      className={`px-3 py-1 rounded-full text-xs font-medium border transition-colors ${
                        selectedStatus === status
                          ? "bg-[#42D5AE] text-white border-[#42D5AE]"
                          : "bg-white text-gray-700 border-gray-300 hover:bg-[#42D5AE]/10 hover:border-[#42D5AE]/30"
                      }`}
                    >
                      {status}
                    </button>
                  ))}
                </div>

                {/* Visibility Filter */}
                <div className="flex flex-wrap gap-2">
                  <span className="text-sm font-medium text-gray-700 mr-2">
                    Visibility:
                  </span>
                  {visibilities.map((visibility) => (
                    <button
                      key={visibility}
                      onClick={() => setSelectedVisibility(visibility)}
                      className={`px-3 py-1 rounded-full text-xs font-medium border transition-colors ${
                        selectedVisibility === visibility
                          ? "bg-[#42D5AE] text-white border-[#42D5AE]"
                          : "bg-white text-gray-700 border-gray-300 hover:bg-[#42D5AE]/10 hover:border-[#42D5AE]/30"
                      }`}
                    >
                      {visibility}
                    </button>
                  ))}
                </div>

                {/* Clear Filters */}
                <button
                  onClick={clearFilters}
                  className="text-sm text-gray-600 hover:text-gray-800 transition-colors"
                >
                  Clear All
                </button>
              </motion.div>
            )}
          </AnimatePresence>
        </div>
      </section>

      {/* Results */}
      <section className="py-8">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          {/* Results Count */}
          <div className="flex justify-between items-center mb-6">
            <p className="text-gray-600">
              Showing {startIndex + 1}-
              {Math.min(
                startIndex + ITEMS_PER_PAGE,
                filteredAndSortedProjects.length
              )}{" "}
              of {filteredAndSortedProjects.length} projects
            </p>
          </div>

          {/* Projects Grid */}
          <AnimatePresence mode="wait">
            {paginatedProjects.length > 0 ? (
              <motion.div
                key={currentPage}
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                exit={{ opacity: 0, y: -20 }}
                transition={{ duration: 0.3 }}
                className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8"
              >
                {paginatedProjects.map((project, index) => (
                  <motion.div
                    key={project.id}
                    initial={{ opacity: 0, y: 20 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ delay: index * 0.1 }}
                  >
                    <ProjectCard
                      project={project}
                      onEdit={handleEditProject}
                      onDelete={handleDeleteProject}
                      onView={handleViewProject}
                    />
                  </motion.div>
                ))}
              </motion.div>
            ) : (
              <motion.div
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                className="text-center py-12"
              >
                <div className="text-gray-400 mb-4">
                  <FolderOpen className="h-12 w-12 mx-auto" />
                </div>
                <h3 className="text-lg font-medium text-gray-900 mb-2">
                  No projects found
                </h3>
                <p className="text-gray-600 mb-4">
                  {searchTerm ||
                  selectedCategory !== "All" ||
                  selectedStatus !== "All" ||
                  selectedVisibility !== "All"
                    ? "Try adjusting your search criteria or filters."
                    : "Create your first project to get started."}
                </p>
                <div className="flex gap-4 justify-center">
                  {(searchTerm ||
                    selectedCategory !== "All" ||
                    selectedStatus !== "All" ||
                    selectedVisibility !== "All") && (
                    <button
                      onClick={clearFilters}
                      className="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors"
                    >
                      Clear Filters
                    </button>
                  )}
                  <button
                    onClick={handleCreateProject}
                    className="px-4 py-2 bg-[#42D5AE] text-white rounded-lg hover:bg-[#38b28d] transition-colors flex items-center gap-2"
                  >
                    <Plus className="w-4 h-4" />
                    Create Project
                  </button>
                </div>
              </motion.div>
            )}
          </AnimatePresence>

          {/* Pagination */}
          <Pagination
            currentPage={currentPage}
            setCurrentPage={setCurrentPage}
            totalPages={totalPages}
          />
        </div>
      </section>

      {/* Create/Edit Project Modal */}
      <AnimatePresence>
        {isModalOpen && (
          <ProjectModal
            isOpen={isModalOpen}
            onClose={() => setIsModalOpen(false)}
            project={editingProject}
            onSave={handleSaveProject}
          />
        )}
      </AnimatePresence>
    </div>
  );
}
