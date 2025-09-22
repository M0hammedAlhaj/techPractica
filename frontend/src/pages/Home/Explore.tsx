"use client";

import type React from "react";
import { useState, useMemo, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import {
  FiSearch,
  FiFilter,
  FiChevronLeft,
  FiChevronRight,
  FiGrid,
  FiList,
  FiSliders,
  FiStar,
  FiClock,
  FiUser,
  FiArrowRight,
} from "react-icons/fi";
import {
  FaReact,
  FaNodeJs,
  FaPython,
  FaJs,
  FaHtml5,
  FaCss3Alt,
  FaVuejs,
  FaDocker,
  FaAws,
  FaDatabase,
  FaMobile,
  FaBrain,
  FaCode,
  FaServer,
  FaCloud,
  FaBitcoin,
} from "react-icons/fa";
import { SiFlutter, SiGraphql, SiKubernetes, SiSolidity } from "react-icons/si";
import { useNavigate } from "react-router-dom";
import { useSystems } from "../../api";
import ExploreProjectCard from "../../components/Cards/ExploreProjectCard";

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

// Icon mapping for technologies
const techIcons: {
  [key: string]: React.ComponentType<{ className?: string }>;
} = {
  React: FaReact,
  "Node.js": FaNodeJs,
  Python: FaPython,
  JavaScript: FaJs,
  HTML5: FaHtml5,
  CSS3: FaCss3Alt,
  "Vue.js": FaVuejs,
  Docker: FaDocker,
  AWS: FaAws,
  MongoDB: FaDatabase,
  Flutter: SiFlutter,
  GraphQL: SiGraphql,
  Kubernetes: SiKubernetes,
  Solidity: SiSolidity,
};

// Category icons
const categoryIcons: {
  [key: string]: React.ComponentType<{ className?: string }>;
} = {
  Frontend: FaCode,
  Backend: FaServer,
  "Full Stack": FaReact,
  Mobile: FaMobile,
  "AI/ML": FaBrain,
  DevOps: FaDocker,
  Cloud: FaCloud,
  Blockchain: FaBitcoin,
};

// Mock data - replace with your actual data
const mockProjects = [
  {
    id: 1,
    sessionName: "Build a Full-Stack E-commerce App with React & Node.js",
    sessionDescription:
      "Learn to create a complete e-commerce platform with user authentication, payment integration, and admin dashboard. This comprehensive project covers both frontend and backend development with modern technologies.",
    technologies: [
      "React",
      "Node.js",
      "MongoDB",
      "Express",
      "Stripe",
      "JWT",
      "Redux",
    ],
    system: "Full Stack",
    ownerName: "Sarah Johnson",
    createdAt: "2024-01-15",
    duration: "4-6 hours",
    difficulty: "Advanced" as const,
    rating: 4.9,
    participants: 156,
  },
  {
    id: 2,
    sessionName: "Machine Learning with Python: Beginner to Pro",
    sessionDescription:
      "Master machine learning fundamentals and build real-world ML models. Covers data preprocessing, model training, evaluation, and deployment strategies using popular Python libraries.",
    technologies: [
      "Python",
      "Scikit-learn",
      "Pandas",
      "NumPy",
      "Matplotlib",
      "TensorFlow",
    ],
    system: "AI/ML",
    ownerName: "Dr. Michael Chen",
    createdAt: "2024-01-12",
    duration: "3-4 hours",
    difficulty: "Intermediate" as const,
    rating: 4.7,
    participants: 89,
  },
  {
    id: 3,
    sessionName: "Mobile App Development with React Native",
    sessionDescription:
      "Create cross-platform mobile applications using React Native. Build apps for both iOS and Android with a single codebase, including navigation, state management, and API integration.",
    technologies: [
      "React Native",
      "JavaScript",
      "Expo",
      "Firebase",
      "AsyncStorage",
    ],
    system: "Mobile",
    ownerName: "Alex Rodriguez",
    createdAt: "2024-01-10",
    duration: "2-3 hours",
    difficulty: "Beginner" as const,
    rating: 4.6,
    participants: 67,
  },
  {
    id: 4,
    sessionName: "Advanced CSS Animations and Interactions",
    sessionDescription:
      "Master modern CSS techniques including animations, transitions, and interactive elements. Learn to create stunning user interfaces with pure CSS and modern layout systems.",
    technologies: ["CSS3", "HTML5", "SASS", "JavaScript", "Framer Motion"],
    system: "Frontend",
    ownerName: "Emma Wilson",
    createdAt: "2024-01-08",
    duration: "2-3 hours",
    difficulty: "Intermediate" as const,
    rating: 4.8,
    participants: 134,
  },
  {
    id: 5,
    sessionName: "RESTful API Development with Express.js",
    sessionDescription:
      "Build robust and scalable REST APIs using Express.js and Node.js. Learn about middleware, authentication, database integration, and API documentation best practices.",
    technologies: [
      "Node.js",
      "Express",
      "MongoDB",
      "JWT",
      "Swagger",
      "Postman",
    ],
    system: "Backend",
    ownerName: "James Thompson",
    createdAt: "2024-01-05",
    duration: "3-4 hours",
    difficulty: "Intermediate" as const,
    rating: 4.5,
    participants: 92,
  },
  {
    id: 6,
    sessionName: "Vue.js 3 Composition API Deep Dive",
    sessionDescription:
      "Explore the latest Vue.js 3 features including the Composition API, Teleport, Fragments, and more. Build modern, reactive applications with improved performance and developer experience.",
    technologies: ["Vue.js", "JavaScript", "Vite", "Pinia", "Vue Router"],
    system: "Frontend",
    ownerName: "Lisa Chang",
    createdAt: "2024-01-03",
    duration: "3-4 hours",
    difficulty: "Advanced" as const,
    rating: 4.7,
    participants: 78,
  },
  {
    id: 7,
    sessionName: "Docker and Kubernetes for Developers",
    sessionDescription:
      "Learn containerization with Docker and orchestration with Kubernetes. Deploy scalable applications in the cloud with modern DevOps practices and container management.",
    technologies: ["Docker", "Kubernetes", "AWS", "Linux", "YAML", "Helm"],
    system: "DevOps",
    ownerName: "Robert Kim",
    createdAt: "2024-01-01",
    duration: "4-5 hours",
    difficulty: "Advanced" as const,
    rating: 4.6,
    participants: 45,
  },
  {
    id: 8,
    sessionName: "Flutter Mobile App Development",
    sessionDescription:
      "Create beautiful, natively compiled applications for mobile from a single codebase using Flutter and Dart. Learn widgets, state management, and platform-specific features.",
    technologies: ["Flutter", "Dart", "Firebase", "Provider", "SQLite"],
    system: "Mobile",
    ownerName: "Priya Patel",
    createdAt: "2023-12-28",
    duration: "3-4 hours",
    difficulty: "Beginner" as const,
    rating: 4.4,
    participants: 112,
  },
  {
    id: 9,
    sessionName: "GraphQL API with Apollo Server",
    sessionDescription:
      "Build modern GraphQL APIs with Apollo Server. Learn schema design, resolvers, subscriptions, and how to integrate with various databases and services.",
    technologies: ["GraphQL", "Apollo", "Node.js", "TypeScript", "Prisma"],
    system: "Backend",
    ownerName: "David Martinez",
    createdAt: "2023-12-25",
    duration: "3-4 hours",
    difficulty: "Advanced" as const,
    rating: 4.8,
    participants: 63,
  },
  {
    id: 10,
    sessionName: "Data Visualization with D3.js",
    sessionDescription:
      "Create interactive and dynamic data visualizations using D3.js. Learn to transform data into compelling visual stories with charts, graphs, and interactive elements.",
    technologies: ["D3.js", "JavaScript", "SVG", "HTML5", "CSS3"],
    system: "Frontend",
    ownerName: "Anna Kowalski",
    createdAt: "2023-12-22",
    duration: "2-3 hours",
    difficulty: "Intermediate" as const,
    rating: 4.5,
    participants: 87,
  },
  {
    id: 11,
    sessionName: "AWS Cloud Architecture Fundamentals",
    sessionDescription:
      "Learn cloud computing fundamentals with AWS. Explore EC2, S3, RDS, Lambda, and other core services. Design scalable and cost-effective cloud architectures.",
    technologies: ["AWS", "EC2", "S3", "Lambda", "RDS", "CloudFormation"],
    system: "Cloud",
    ownerName: "Mark Johnson",
    createdAt: "2023-12-20",
    duration: "4-5 hours",
    difficulty: "Intermediate" as const,
    rating: 4.6,
    participants: 156,
  },
  {
    id: 12,
    sessionName: "Blockchain Development with Solidity",
    sessionDescription:
      "Enter the world of blockchain development. Learn Solidity programming, smart contract development, and how to build decentralized applications (DApps) on Ethereum.",
    technologies: ["Solidity", "Ethereum", "Web3.js", "Truffle", "MetaMask"],
    system: "Blockchain",
    ownerName: "Chris Anderson",
    createdAt: "2023-12-18",
    duration: "4-6 hours",
    difficulty: "Advanced" as const,
    rating: 4.7,
    participants: 34,
  },
];

const categories = [
  "All",
  "Frontend",
  "Backend",
  "Full Stack",
  "Mobile",
  "AI/ML",
  "DevOps",
  "Cloud",
  "Blockchain",
];
const difficulties = ["All", "Beginner", "Intermediate", "Advanced"];
const sortOptions = [
  { value: "newest", label: "Newest First" },
  { value: "oldest", label: "Oldest First" },
  { value: "rating", label: "Highest Rated" },
  { value: "participants", label: "Most Popular" },
  { value: "name", label: "Name A-Z" },
];

const ITEMS_PER_PAGE = 6;

// Custom Project Card Component

export default function Explore() {
  const router = useNavigate();
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("All");
  const [selectedDifficulty, setSelectedDifficulty] = useState("All");
  const [sortBy, setSortBy] = useState("newest");
  const [currentPage, setCurrentPage] = useState(1);
  const [viewMode, setViewMode] = useState<"grid" | "list">("grid");
  const [showFilters, setShowFilters] = useState(false);
  const SystemsData = useSystems();

  const systems = SystemsData.data?.data?.systems.name;
  console.log(systems);
  const isDesktop = useIsDesktop();

  // Filter and sort projects
  const filteredAndSortedProjects = useMemo(() => {
    const filtered = mockProjects.filter((project) => {
      const matchesSearch =
        project.sessionName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        project.sessionDescription
          .toLowerCase()
          .includes(searchTerm.toLowerCase()) ||
        project.technologies.some((tech) =>
          tech.toLowerCase().includes(searchTerm.toLowerCase())
        );

      const matchesCategory =
        selectedCategory === "All" || project.system === selectedCategory;
      const matchesDifficulty =
        selectedDifficulty === "All" ||
        project.difficulty === selectedDifficulty;

      return matchesSearch && matchesCategory && matchesDifficulty;
    });

    // Sort projects
    filtered.sort((a, b) => {
      switch (sortBy) {
        case "newest":
          return (
            new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
          );
        case "oldest":
          return (
            new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime()
          );
        case "rating":
          return b.rating - a.rating;
        case "participants":
          return b.participants - a.participants;
        case "name":
          return a.sessionName.localeCompare(b.sessionName);
        default:
          return 0;
      }
    });

    return filtered;
  }, [searchTerm, selectedCategory, selectedDifficulty, sortBy]);

  // Pagination
  const totalPages = Math.ceil(
    filteredAndSortedProjects.length / ITEMS_PER_PAGE
  );
  const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
  const paginatedProjects = filteredAndSortedProjects.slice(
    startIndex,
    startIndex + ITEMS_PER_PAGE
  );

  // Reset page when filters change
  const handleFilterChange = () => {
    setCurrentPage(1);
  };

  const clearFilters = () => {
    setSearchTerm("");
    setSelectedCategory("All");
    setSelectedDifficulty("All");
    setSortBy("newest");
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
            className="text-center text-white"
          >
            <h1 className="text-4xl md:text-5xl font-bold mb-4">
              Discover Amazing Projects
            </h1>
            <p className="text-xl text-[#42D5AE]/80 max-w-2xl mx-auto">
              Explore hands-on projects created by our community. Learn by
              building real-world applications.
            </p>
          </motion.div>
        </div>
      </section>

      {/* Filters and Search */}
      <section className="py-8 bg-white border-b">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex flex-col lg:flex-row gap-4 items-start lg:items-center justify-between">
            {/* Search */}
            <div className="relative flex-1 max-w-md">
              <FiSearch className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
              <input
                type="text"
                placeholder="Search projects, technologies..."
                value={searchTerm}
                onChange={(e) => {
                  setSearchTerm(e.target.value);
                  handleFilterChange();
                }}
                className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-[#42D5AE] focus:border-transparent outline-none transition-all"
              />
            </div>

            {/* Filter Toggle (Mobile) */}
            <div className="flex items-center gap-4">
              <button
                onClick={() => setShowFilters(!showFilters)}
                className="lg:hidden flex items-center gap-2 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors"
              >
                <FiSliders className="h-4 w-4" />
                Filters
              </button>

              {/* View Mode Toggle */}
              <div className="flex items-center border border-gray-300 rounded-lg p-1">
                <button
                  onClick={() => setViewMode("grid")}
                  className={`h-8 w-8 p-0 rounded flex items-center justify-center transition-colors ${
                    viewMode === "grid"
                      ? "bg-[#42D5AE] text-white"
                      : "hover:bg-gray-100"
                  }`}
                >
                  <FiGrid className="h-4 w-4" />
                </button>
                <button
                  onClick={() => setViewMode("list")}
                  className={`h-8 w-8 p-0 rounded flex items-center justify-center transition-colors ${
                    viewMode === "list"
                      ? "bg-[#42D5AE] text-white"
                      : "hover:bg-gray-100"
                  }`}
                >
                  <FiList className="h-4 w-4" />
                </button>
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
                  {categories.map((category) => {
                    const CategoryIcon = categoryIcons[category] || FaCode;
                    return (
                      <button
                        key={category}
                        onClick={() => {
                          setSelectedCategory(category);
                          handleFilterChange();
                        }}
                        className={`px-3 py-1 rounded-full text-xs font-medium border transition-colors flex items-center gap-1 ${
                          selectedCategory === category
                            ? "bg-[#42D5AE] text-white border-[#42D5AE]"
                            : "bg-white text-gray-700 border-gray-300 hover:bg-[#42D5AE]/10 hover:border-[#42D5AE]/30"
                        }`}
                      >
                        {category !== "All" && (
                          <CategoryIcon className="w-3 h-3" />
                        )}
                        {category}
                      </button>
                    );
                  })}
                </div>

                {/* Difficulty Filter */}
                <div className="flex flex-wrap gap-2">
                  <span className="text-sm font-medium text-gray-700 mr-2">
                    Difficulty:
                  </span>
                  {difficulties.map((difficulty) => (
                    <button
                      key={difficulty}
                      onClick={() => {
                        setSelectedDifficulty(difficulty);
                        handleFilterChange();
                      }}
                      className={`px-3 py-1 rounded-full text-xs font-medium border transition-colors ${
                        selectedDifficulty === difficulty
                          ? "bg-[#42D5AE] text-white border-[#42D5AE]"
                          : "bg-white text-gray-700 border-gray-300 hover:bg-[#42D5AE]/10 hover:border-[#42D5AE]/30"
                      }`}
                    >
                      {difficulty}
                    </button>
                  ))}
                </div>

                {/* Sort */}
                <div className="flex items-center gap-2">
                  <span className="text-sm font-medium text-gray-700">
                    Sort by:
                  </span>
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

          {/* Projects Grid/List */}
          <AnimatePresence mode="wait">
            {paginatedProjects.length > 0 ? (
              <motion.div
                key={`${viewMode}-${currentPage}`}
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                exit={{ opacity: 0, y: -20 }}
                transition={{ duration: 0.3 }}
                className={
                  viewMode === "grid"
                    ? "grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8"
                    : "space-y-6"
                }
              >
                {paginatedProjects.map((project, index) => (
                  <motion.div
                    key={project.id}
                    initial={{ opacity: 0, y: 20 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ delay: index * 0.1 }}
                  >
                    <ExploreProjectCard
                      project={project}
                      onClick={() => router(`/projects/${project.id}`)}
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
                  <FiFilter className="h-12 w-12 mx-auto" />
                </div>
                <h3 className="text-lg font-medium text-gray-900 mb-2">
                  No projects found
                </h3>
                <p className="text-gray-600 mb-4">
                  Try adjusting your search criteria or filters to find more
                  projects.
                </p>
                <button
                  onClick={clearFilters}
                  className="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors"
                >
                  Clear Filters
                </button>
              </motion.div>
            )}
          </AnimatePresence>

          {/* Pagination */}
          {totalPages > 1 && (
            <motion.div
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              transition={{ delay: 0.3 }}
              className="flex justify-center items-center gap-2 mt-12"
            >
              <button
                onClick={() => setCurrentPage(Math.max(1, currentPage - 1))}
                disabled={currentPage === 1}
                className="flex items-center gap-2 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <FiChevronLeft className="h-4 w-4" />
                Previous
              </button>

              <div className="flex items-center gap-1">
                {Array.from({ length: totalPages }, (_, i) => i + 1).map(
                  (page) => {
                    if (
                      page === 1 ||
                      page === totalPages ||
                      (page >= currentPage - 1 && page <= currentPage + 1)
                    ) {
                      return (
                        <button
                          key={page}
                          onClick={() => setCurrentPage(page)}
                          className={`w-10 h-10 rounded-lg transition-colors ${
                            currentPage === page
                              ? "bg-[#42D5AE] text-white"
                              : "border border-gray-300 hover:bg-gray-50"
                          }`}
                        >
                          {page}
                        </button>
                      );
                    } else if (
                      page === currentPage - 2 ||
                      page === currentPage + 2
                    ) {
                      return (
                        <span key={page} className="px-2 text-gray-400">
                          ...
                        </span>
                      );
                    }
                    return null;
                  }
                )}
              </div>

              <button
                onClick={() =>
                  setCurrentPage(Math.min(totalPages, currentPage + 1))
                }
                disabled={currentPage === totalPages}
                className="flex items-center gap-2 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
              >
                Next
                <FiChevronRight className="h-4 w-4" />
              </button>
            </motion.div>
          )}
        </div>
      </section>
    </div>
  );
}
