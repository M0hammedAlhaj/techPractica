import { useState, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";

import { Menu, X, LogOut } from "lucide-react";
import { Link, useLocation } from "react-router-dom";
import { NavLinks } from "../../data/data";
import { CookiesService } from "../../imports";

// Mock navigation links - replace with your actual routes

export default function Navbar() {
  const [isOpen, setIsOpen] = useState(false);
  const [isScrolled, setIsScrolled] = useState(false);
  const [token, setToken] = useState<string | null>(null);
  const [mousePosition, setMousePosition] = useState({ x: 0, y: 0 });

  const location = useLocation();
  const pathname = location.pathname;
  // Handle scroll effect for glassmorphism
  useEffect(() => {
    const handleScroll = () => {
      setIsScrolled(window.scrollY > 20);
    };
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  // Track mouse position for interactive effects
  useEffect(() => {
    const handleMouseMove = (e: MouseEvent) => {
      setMousePosition({ x: e.clientX, y: e.clientY });
    };
    window.addEventListener("mousemove", handleMouseMove);
    return () => window.removeEventListener("mousemove", handleMouseMove);
  }, []);

  // Mock token check - replace with your actual auth logic
  useEffect(() => {
    const userToken = CookiesService.get("UserToken");
    setToken(userToken);
  }, []);

  const filteredLinks = NavLinks.filter(({ label }) => {
    if (token) {
      return label !== "Login" && label !== "Join";
    } else {
      return label !== "Profile" && label !== "Sessions";
    }
  });

  const handleLogout = () => {
    localStorage.removeItem("UserToken");
    setToken(null);
    setIsOpen(false);
  };

  const closeMenu = () => setIsOpen(false);

  return (
    <>
      {/* Modern Glassmorphism Navbar with Home Page Colors */}
      <motion.nav
        initial={{ y: -100, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ duration: 0.8, ease: [0.16, 1, 0.3, 1] }}
        className={`fixed top-4 left-4 right-4 z-50 transition-all duration-700 ${
          isScrolled
            ? "bg-[#022639]/20 backdrop-blur-2xl border border-[#42D5AE]/30 shadow-2xl shadow-[#42D5AE]/10"
            : "bg-[#022639]/15 backdrop-blur-xl border border-[#42D5AE]/20 shadow-xl shadow-[#022639]/5"
        } rounded-2xl`}
        style={{
          background: isScrolled
            ? `radial-gradient(600px circle at ${mousePosition.x}px ${mousePosition.y}px, rgba(66, 213, 174, 0.15), rgba(2, 38, 57, 0.1), transparent 40%)`
            : `radial-gradient(400px circle at ${mousePosition.x}px ${mousePosition.y}px, rgba(66, 213, 174, 0.1), transparent 30%)`,
        }}
      >
        {/* Enhanced animated border gradient with home colors */}
        <motion.div
          className="absolute inset-0 rounded-2xl p-[1px]"
          animate={{
            background: [
              "linear-gradient(45deg, rgba(66, 213, 174, 0.3), rgba(56, 178, 141, 0.3), rgba(2, 38, 57, 0.3), rgba(66, 213, 174, 0.3))",
              "linear-gradient(45deg, rgba(56, 178, 141, 0.3), rgba(2, 38, 57, 0.3), rgba(66, 213, 174, 0.3), rgba(56, 178, 141, 0.3))",
              "linear-gradient(45deg, rgba(2, 38, 57, 0.3), rgba(66, 213, 174, 0.3), rgba(56, 178, 141, 0.3), rgba(2, 38, 57, 0.3))",
            ],
          }}
          transition={{
            duration: 4,
            repeat: Number.POSITIVE_INFINITY,
            ease: "linear",
          }}
        >
          <div className="h-full w-full rounded-2xl bg-[#022639]/30 backdrop-blur-2xl" />
        </motion.div>

        {/* Enhanced floating orbs background with home colors */}
        <div className="absolute inset-0 overflow-hidden rounded-2xl">
          <motion.div
            className="absolute -top-6 -left-6 w-32 h-32 bg-gradient-to-br from-[#42D5AE]/20 to-[#38b28d]/20 rounded-full blur-2xl"
            animate={{
              x: [0, 40, 0],
              y: [0, -30, 0],
              scale: [1, 1.3, 1],
            }}
            transition={{
              duration: 10,
              repeat: Number.POSITIVE_INFINITY,
              ease: "easeInOut",
            }}
          />
          <motion.div
            className="absolute -bottom-6 -right-6 w-40 h-40 bg-gradient-to-br from-[#022639]/20 to-[#0a3a5a]/20 rounded-full blur-2xl"
            animate={{
              x: [0, -30, 0],
              y: [0, 20, 0],
              scale: [1, 0.8, 1],
            }}
            transition={{
              duration: 8,
              repeat: Number.POSITIVE_INFINITY,
              ease: "easeInOut",
            }}
          />
          <motion.div
            className="absolute top-1/2 left-1/2 w-24 h-24 bg-gradient-to-br from-[#42D5AE]/15 to-[#38b28d]/15 rounded-full blur-xl"
            animate={{
              x: [-12, 12, -12],
              y: [-12, 12, -12],
              scale: [1, 1.2, 1],
            }}
            transition={{
              duration: 6,
              repeat: Number.POSITIVE_INFINITY,
              ease: "easeInOut",
            }}
          />
        </div>

        <div className="relative z-10 max-w-7xl mx-auto px-6">
          <div className="flex items-center justify-between h-16">
            {/* Modern Text-Based Logo Design with Home Colors */}
            <motion.div
              className="flex items-center"
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
            >
              <Link to="/" className="flex items-center group">
                <div className="relative">
                  {/* Logo icon design with home colors */}
                  <motion.div
                    className="relative w-12 h-12 rounded-xl bg-gradient-to-br from-[#42D5AE] via-[#38b28d] to-[#022639] shadow-lg shadow-[#42D5AE]/30 group-hover:shadow-xl group-hover:shadow-[#42D5AE]/40 transition-all duration-300 flex items-center justify-center"
                    whileHover={{ rotate: 5 }}
                    transition={{ duration: 0.3 }}
                  >
                    {/* Geometric logo pattern */}
                    <div className="relative">
                      <motion.div
                        className="w-6 h-6 border-2 border-white/80 rounded-md"
                        animate={{ rotate: 360 }}
                        transition={{
                          duration: 20,
                          repeat: Number.POSITIVE_INFINITY,
                          ease: "linear",
                        }}
                      />
                      <motion.div
                        className="absolute inset-1 bg-white/20 rounded-sm"
                        animate={{
                          scale: [1, 1.2, 1],
                          opacity: [0.2, 0.5, 0.2],
                        }}
                        transition={{
                          duration: 2,
                          repeat: Number.POSITIVE_INFINITY,
                          ease: "easeInOut",
                        }}
                      />
                    </div>

                    {/* Animated ring around logo */}
                    <motion.div
                      className="absolute inset-0 rounded-xl border-2 border-[#42D5AE]/50"
                      animate={{ rotate: -360 }}
                      transition={{
                        duration: 15,
                        repeat: Number.POSITIVE_INFINITY,
                        ease: "linear",
                      }}
                    />
                  </motion.div>

                  {/* Pulsing glow effect with home colors */}
                  <motion.div
                    className="absolute inset-0 rounded-xl bg-gradient-to-br from-[#42D5AE]/40 to-[#38b28d]/40 blur-lg"
                    animate={{
                      scale: [1, 1.4, 1],
                      opacity: [0.3, 0, 0.3],
                    }}
                    transition={{
                      duration: 3,
                      repeat: Number.POSITIVE_INFINITY,
                      ease: "easeInOut",
                    }}
                  />
                </div>

                {/* Enhanced brand text with home colors */}
                <div className="ml-4">
                  <motion.div
                    className="flex items-baseline"
                    whileHover={{
                      scale: 1.02,
                    }}
                  >
                    <span className="text-2xl font-black bg-gradient-to-r from-white via-gray-100 to-[#42D5AE]/80 bg-clip-text text-transparent tracking-tight">
                      Tech
                    </span>
                    <motion.span
                      className="text-2xl font-black bg-gradient-to-r from-[#42D5AE] via-[#38b28d] to-[#022639] bg-clip-text text-transparent tracking-tight"
                      animate={{
                        background: [
                          "linear-gradient(to right, #42D5AE, #38b28d, #022639)",
                          "linear-gradient(to right, #38b28d, #022639, #42D5AE)",
                          "linear-gradient(to right, #022639, #42D5AE, #38b28d)",
                        ],
                      }}
                      transition={{
                        duration: 3,
                        repeat: Number.POSITIVE_INFINITY,
                        ease: "easeInOut",
                      }}
                    >
                      Practica
                    </motion.span>
                  </motion.div>
                  <motion.div
                    className="text-xs font-medium text-[#42D5AE]/70 tracking-widest uppercase"
                    initial={{ opacity: 0, y: 5 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ delay: 0.5 }}
                  >
                    Learn • Build • Grow
                  </motion.div>
                </div>
              </Link>
            </motion.div>

            {/* Enhanced Desktop Navigation with Home Colors */}
            <div className="hidden md:flex items-center">
              {/* Navigation pills container with home colors */}
              <div className="flex items-center space-x-1 bg-[#022639]/30 backdrop-blur-xl rounded-full p-1.5 border border-[#42D5AE]/30 shadow-lg shadow-[#42D5AE]/10">
                {filteredLinks.map(({ label, path }, index) => {
                  const isActive =
                    pathname === path ||
                    (label === "Sessions" && pathname === "/sessions");
                  const linkPath = label === "Sessions" ? "/sessions" : path;

                  return (
                    <motion.div
                      key={index}
                      whileHover={{ scale: 1.05 }}
                      whileTap={{ scale: 0.95 }}
                      transition={{ duration: 0.2, ease: "easeOut" }}
                    >
                      <Link
                        to={linkPath}
                        className={`relative px-5 py-2.5 text-sm font-semibold transition-all duration-300 rounded-full ${
                          isActive
                            ? "text-white bg-gradient-to-r from-[#42D5AE] via-[#38b28d] to-[#42D5AE] shadow-lg shadow-[#42D5AE]/30"
                            : "text-gray-300 hover:text-white hover:bg-[#022639]/50"
                        }`}
                      >
                        {/* Enhanced active pill background */}
                        {isActive && (
                          <motion.div
                            layoutId="activePill"
                            className="absolute inset-0 bg-gradient-to-r from-[#42D5AE] via-[#38b28d] to-[#42D5AE] rounded-full"
                            initial={false}
                            transition={{
                              type: "spring",
                              stiffness: 500,
                              damping: 30,
                            }}
                          />
                        )}

                        <span className="relative z-10">{label}</span>

                        {/* Enhanced hover glow effect */}
                        {!isActive && (
                          <motion.div
                            className="absolute inset-0 rounded-full bg-gradient-to-r from-[#42D5AE]/20 to-[#38b28d]/20 opacity-0"
                            whileHover={{ opacity: 1 }}
                            transition={{ duration: 0.2 }}
                          />
                        )}
                      </Link>
                    </motion.div>
                  );
                })}
              </div>

              {/* Enhanced Logout Button with Home Colors */}
              {token && (
                <motion.div
                  whileHover={{ scale: 1.05 }}
                  whileTap={{ scale: 0.95 }}
                  className="ml-4"
                >
                  <button
                    onClick={handleLogout}
                    className="flex items-center space-x-2 px-5 py-2.5 text-sm font-semibold text-red-300 hover:text-white bg-red-500/10 hover:bg-red-500/20 border border-red-500/30 hover:border-red-400/50 rounded-full transition-all duration-300 backdrop-blur-xl shadow-lg shadow-red-500/10"
                  >
                    <LogOut className="w-4 h-4" />
                    <span>Logout</span>
                  </button>
                </motion.div>
              )}
            </div>

            {/* Enhanced Mobile Menu Button with Home Colors */}
            <div className="md:hidden">
              <motion.button
                whileTap={{ scale: 0.9 }}
                onClick={() => setIsOpen(!isOpen)}
                className="relative p-3 bg-[#022639]/40 backdrop-blur-xl border border-[#42D5AE]/40 rounded-full hover:bg-[#022639]/60 transition-all duration-300 shadow-lg shadow-[#42D5AE]/10"
                aria-label="Toggle menu"
              >
                <AnimatePresence mode="wait">
                  {isOpen ? (
                    <motion.div
                      key="close"
                      initial={{ rotate: -90, opacity: 0 }}
                      animate={{ rotate: 0, opacity: 1 }}
                      exit={{ rotate: 90, opacity: 0 }}
                      transition={{ duration: 0.2 }}
                    >
                      <X className="w-5 h-5 text-white" />
                    </motion.div>
                  ) : (
                    <motion.div
                      key="menu"
                      initial={{ rotate: 90, opacity: 0 }}
                      animate={{ rotate: 0, opacity: 1 }}
                      exit={{ rotate: -90, opacity: 0 }}
                      transition={{ duration: 0.2 }}
                    >
                      <Menu className="w-5 h-5 text-white" />
                    </motion.div>
                  )}
                </AnimatePresence>
              </motion.button>
            </div>
          </div>
        </div>
      </motion.nav>

      {/* Enhanced Mobile Menu with Home Colors */}
      <AnimatePresence>
        {isOpen && (
          <>
            {/* Enhanced backdrop */}
            <motion.div
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
              transition={{ duration: 0.3 }}
              className="fixed inset-0 z-40 bg-[#022639]/30 backdrop-blur-sm"
              onClick={closeMenu}
            />

            {/* Enhanced mobile menu panel */}
            <motion.div
              initial={{ opacity: 0, scale: 0.95, y: -20 }}
              animate={{ opacity: 1, scale: 1, y: 0 }}
              exit={{ opacity: 0, scale: 0.95, y: -20 }}
              transition={{ duration: 0.3, ease: [0.16, 1, 0.3, 1] }}
              className="fixed top-24 left-4 right-4 z-50 bg-[#022639]/40 backdrop-blur-2xl border border-[#42D5AE]/30 rounded-2xl shadow-2xl overflow-hidden"
            >
              {/* Enhanced menu background effects */}
              <div className="absolute inset-0">
                <motion.div
                  className="absolute top-0 left-0 w-full h-1 bg-gradient-to-r from-[#42D5AE] via-[#38b28d] to-[#42D5AE]"
                  animate={{
                    background: [
                      "linear-gradient(to right, #42D5AE, #38b28d, #42D5AE)",
                      "linear-gradient(to right, #38b28d, #42D5AE, #38b28d)",
                      "linear-gradient(to right, #42D5AE, #38b28d, #42D5AE)",
                    ],
                  }}
                  transition={{ duration: 3, repeat: Number.POSITIVE_INFINITY }}
                />
                <motion.div
                  className="absolute top-6 right-6 w-24 h-24 bg-gradient-to-br from-[#42D5AE]/20 to-[#38b28d]/20 rounded-full blur-2xl"
                  animate={{
                    scale: [1, 1.3, 1],
                    opacity: [0.3, 0.6, 0.3],
                  }}
                  transition={{ duration: 4, repeat: Number.POSITIVE_INFINITY }}
                />
              </div>

              <div className="relative z-10 p-6 space-y-2">
                {filteredLinks.map(({ label, path }, index) => {
                  const isActive =
                    pathname === path ||
                    (label === "Sessions" && pathname === "/sessions");
                  const linkPath = label === "Sessions" ? "/sessions" : path;

                  return (
                    <motion.div
                      key={label}
                      initial={{ opacity: 0, x: -20 }}
                      animate={{ opacity: 1, x: 0 }}
                      transition={{ delay: index * 0.05, duration: 0.3 }}
                    >
                      <Link
                        to={linkPath}
                        className={`block px-6 py-4 text-base font-semibold transition-all duration-300 rounded-xl ${
                          isActive
                            ? "text-white bg-gradient-to-r from-[#42D5AE] via-[#38b28d] to-[#42D5AE] shadow-lg shadow-[#42D5AE]/30"
                            : "text-gray-300 hover:text-white hover:bg-[#022639]/50"
                        }`}
                        onClick={closeMenu}
                      >
                        <span className="relative z-10">{label}</span>
                      </Link>
                    </motion.div>
                  );
                })}

                {/* Enhanced Mobile Logout Button */}
                {token && (
                  <motion.div
                    initial={{ opacity: 0, x: -20 }}
                    animate={{ opacity: 1, x: 0 }}
                    transition={{
                      delay: filteredLinks.length * 0.05,
                      duration: 0.3,
                    }}
                    className="pt-4 border-t border-[#42D5AE]/30"
                  >
                    <button
                      onClick={handleLogout}
                      className="w-full flex items-center space-x-3 px-6 py-4 text-base font-semibold text-red-300 hover:text-white bg-red-500/10 hover:bg-red-500/20 border border-red-500/30 hover:border-red-400/50 rounded-xl transition-all duration-300"
                    >
                      <LogOut className="w-5 h-5" />
                      <span>Logout</span>
                    </button>
                  </motion.div>
                )}
              </div>
            </motion.div>
          </>
        )}
      </AnimatePresence>

      {/* Spacer for fixed navbar */}
      <div className="h-24" />
    </>
  );
}
