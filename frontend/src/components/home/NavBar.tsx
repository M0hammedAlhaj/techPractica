import { useState, useEffect, useCallback } from "react";
import {
  motion,
  AnimatePresence,
  useScroll,
  useTransform,
} from "framer-motion";
import { Menu, X, LogOut, Sparkles, Zap } from "lucide-react";
import { Link, useLocation } from "react-router-dom";
import { NavLinks } from "../../data/data";
import { CookiesService } from "../../imports";

export default function ImprovedNavbar() {
  const [isOpen, setIsOpen] = useState(false);
  const [isScrolled, setIsScrolled] = useState(false);
  const [token, setToken] = useState<string | null>(null);
  const [mousePosition, setMousePosition] = useState({ x: 0, y: 0 });
  const [hoveredItem, setHoveredItem] = useState<string | null>(null);
  const location = useLocation();
  const pathname = location.pathname;

  const { scrollY } = useScroll();
  const navbarOpacity = useTransform(scrollY, [0, 100], [0.95, 0.98]);
  const navbarBlur = useTransform(scrollY, [0, 100], [20, 30]);

  // Enhanced scroll effect with smoother transitions
  useEffect(() => {
    const handleScroll = () => {
      const scrolled = window.scrollY > 20;
      setIsScrolled(scrolled);
    };

    const throttledScroll = throttle(handleScroll, 16); // 60fps
    window.addEventListener("scroll", throttledScroll, { passive: true });
    return () => window.removeEventListener("scroll", throttledScroll);
  }, []);

  // Optimized mouse tracking with throttling
  const handleMouseMove = useCallback(
    throttle((e: MouseEvent) => {
      setMousePosition({ x: e.clientX, y: e.clientY });
    }, 16),
    []
  );

  useEffect(() => {
    window.addEventListener("mousemove", handleMouseMove, { passive: true });
    return () => window.removeEventListener("mousemove", handleMouseMove);
  }, [handleMouseMove]);

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
      {/* Enhanced Glassmorphism Navbar */}
      <motion.nav
        initial={{ y: -100, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ duration: 1, ease: [0.16, 1, 0.3, 1] }}
        style={{
          opacity: navbarOpacity,
          backdropFilter: `blur(${navbarBlur}px)`,
        }}
        className={`fixed top-4 left-4 right-4 z-50 transition-all duration-700 ${
          isScrolled
            ? "bg-gradient-to-r from-[#022639]/25 via-[#022639]/20 to-[#022639]/25"
            : "bg-gradient-to-r from-[#022639]/20 via-[#022639]/15 to-[#022639]/20"
        } rounded-3xl border border-[#42D5AE]/30 shadow-2xl shadow-[#42D5AE]/20`}
      >
        {/* Dynamic gradient border */}
        <motion.div
          className="absolute inset-0 rounded-3xl p-[1px] overflow-hidden"
          animate={{
            background: [
              "conic-gradient(from 0deg, rgba(66, 213, 174, 0.4), rgba(56, 178, 141, 0.2), rgba(2, 38, 57, 0.4), rgba(66, 213, 174, 0.4))",
              "conic-gradient(from 120deg, rgba(66, 213, 174, 0.4), rgba(56, 178, 141, 0.2), rgba(2, 38, 57, 0.4), rgba(66, 213, 174, 0.4))",
              "conic-gradient(from 240deg, rgba(66, 213, 174, 0.4), rgba(56, 178, 141, 0.2), rgba(2, 38, 57, 0.4), rgba(66, 213, 174, 0.4))",
            ],
          }}
          transition={{
            duration: 6,
            repeat: Number.POSITIVE_INFINITY,
            ease: "linear",
          }}
        >
          <div className="h-full w-full rounded-3xl bg-[#022639]/40 backdrop-blur-3xl" />
        </motion.div>

        {/* Enhanced floating particles */}
        <div className="absolute inset-0 overflow-hidden rounded-3xl">
          {[...Array(6)].map((_, i) => (
            <motion.div
              key={i}
              className={`absolute w-2 h-2 bg-gradient-to-r from-[#42D5AE] to-[#38b28d] rounded-full opacity-30`}
              style={{
                left: `${20 + i * 15}%`,
                top: `${30 + (i % 2) * 40}%`,
              }}
              animate={{
                y: [-10, 10, -10],
                x: [-5, 5, -5],
                scale: [0.8, 1.2, 0.8],
                opacity: [0.2, 0.6, 0.2],
              }}
              transition={{
                duration: 3 + i * 0.5,
                repeat: Number.POSITIVE_INFINITY,
                ease: "easeInOut",
                delay: i * 0.2,
              }}
            />
          ))}
        </div>

        {/* Interactive mouse glow */}
        <motion.div
          className="absolute inset-0 rounded-3xl opacity-30"
          style={{
            background: `radial-gradient(300px circle at ${mousePosition.x}px ${mousePosition.y}px, rgba(66, 213, 174, 0.15), transparent 50%)`,
          }}
        />

        <div className="relative z-10 max-w-7xl mx-auto px-8">
          <div className="flex items-center justify-between h-20">
            {/* Enhanced Logo */}
            <motion.div
              className="flex items-center"
              whileHover={{ scale: 1.02 }}
              whileTap={{ scale: 0.98 }}
            >
              <Link to="/" className="flex items-center group">
                <div className="relative">
                  <motion.div
                    className="relative w-14 h-14 rounded-2xl bg-gradient-to-br from-[#42D5AE] via-[#38b28d] to-[#022639] shadow-xl shadow-[#42D5AE]/40 group-hover:shadow-2xl group-hover:shadow-[#42D5AE]/60 transition-all duration-500 flex items-center justify-center overflow-hidden"
                    whileHover={{ rotate: 8, scale: 1.05 }}
                    transition={{ duration: 0.4, ease: "easeOut" }}
                  >
                    {/* Logo icon with enhanced animation */}
                    <motion.div
                      className="relative z-10"
                      animate={{ rotate: 360 }}
                      transition={{
                        duration: 20,
                        repeat: Number.POSITIVE_INFINITY,
                        ease: "linear",
                      }}
                    >
                      <Zap className="w-7 h-7 text-white drop-shadow-lg" />
                    </motion.div>

                    {/* Animated background pattern */}
                    <motion.div
                      className="absolute inset-0 bg-gradient-to-br from-white/20 to-transparent"
                      animate={{
                        scale: [1, 1.2, 1],
                        rotate: [0, 180, 360],
                      }}
                      transition={{
                        duration: 8,
                        repeat: Number.POSITIVE_INFINITY,
                        ease: "easeInOut",
                      }}
                    />

                    {/* Sparkle effects */}
                    <motion.div
                      className="absolute top-1 right-1"
                      animate={{
                        scale: [0, 1, 0],
                        rotate: [0, 180, 360],
                      }}
                      transition={{
                        duration: 2,
                        repeat: Number.POSITIVE_INFINITY,
                        ease: "easeInOut",
                      }}
                    >
                      <Sparkles className="w-3 h-3 text-white/80" />
                    </motion.div>
                  </motion.div>

                  {/* Enhanced pulsing glow */}
                  <motion.div
                    className="absolute inset-0 rounded-2xl bg-gradient-to-br from-[#42D5AE]/60 to-[#38b28d]/60 blur-xl"
                    animate={{
                      scale: [1, 1.5, 1],
                      opacity: [0.4, 0, 0.4],
                    }}
                    transition={{
                      duration: 3,
                      repeat: Number.POSITIVE_INFINITY,
                      ease: "easeInOut",
                    }}
                  />
                </div>

                {/* Enhanced brand text */}
                <div className="ml-5">
                  <motion.div
                    className="flex items-baseline"
                    whileHover={{ scale: 1.02 }}
                  >
                    <span className="text-3xl font-black bg-gradient-to-r from-white via-gray-100 to-[#42D5AE]/90 bg-clip-text text-transparent tracking-tight">
                      Tech
                    </span>
                    <motion.span
                      className="text-3xl font-black bg-gradient-to-r from-[#42D5AE] via-[#38b28d] to-[#42D5AE] bg-clip-text text-transparent tracking-tight ml-1"
                      animate={{
                        backgroundPosition: ["0% 50%", "100% 50%", "0% 50%"],
                      }}
                      transition={{
                        duration: 4,
                        repeat: Number.POSITIVE_INFINITY,
                        ease: "easeInOut",
                      }}
                      style={{
                        backgroundSize: "200% 200%",
                      }}
                    >
                      Practica
                    </motion.span>
                  </motion.div>
                  <motion.div
                    className="text-xs font-semibold text-[#42D5AE]/80 tracking-[0.2em] uppercase"
                    initial={{ opacity: 0, y: 5 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ delay: 0.6 }}
                  >
                    Learn • Build • Grow
                  </motion.div>
                </div>
              </Link>
            </motion.div>

            {/* Enhanced Desktop Navigation */}
            <div className="hidden lg:flex items-center">
              <div className="flex items-center space-x-2 bg-[#022639]/40 backdrop-blur-2xl rounded-full p-2 border border-[#42D5AE]/40 shadow-xl shadow-[#42D5AE]/20">
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
                      onHoverStart={() => setHoveredItem(label)}
                      onHoverEnd={() => setHoveredItem(null)}
                      transition={{ duration: 0.2, ease: "easeOut" }}
                    >
                      <Link
                        to={linkPath}
                        className={`relative px-6 py-3 text-sm font-semibold transition-all duration-300 rounded-full overflow-hidden ${
                          isActive
                            ? "text-white"
                            : "text-gray-300 hover:text-white"
                        }`}
                      >
                        {/* Active background */}
                        {isActive && (
                          <motion.div
                            layoutId="activePill"
                            className="absolute inset-0 bg-gradient-to-r from-[#42D5AE] via-[#38b28d] to-[#42D5AE] rounded-full shadow-lg shadow-[#42D5AE]/40"
                            initial={false}
                            transition={{
                              type: "spring",
                              stiffness: 500,
                              damping: 30,
                            }}
                          />
                        )}

                        {/* Hover effect */}
                        {!isActive && hoveredItem === label && (
                          <motion.div
                            className="absolute inset-0 bg-gradient-to-r from-[#42D5AE]/20 via-[#38b28d]/20 to-[#42D5AE]/20 rounded-full"
                            initial={{ opacity: 0, scale: 0.8 }}
                            animate={{ opacity: 1, scale: 1 }}
                            exit={{ opacity: 0, scale: 0.8 }}
                            transition={{ duration: 0.2 }}
                          />
                        )}

                        <span className="relative z-10">{label}</span>

                        {/* Shimmer effect for active item */}
                        {isActive && (
                          <motion.div
                            className="absolute inset-0 bg-gradient-to-r from-transparent via-white/20 to-transparent rounded-full"
                            animate={{ x: ["-100%", "100%"] }}
                            transition={{
                              duration: 2,
                              repeat: Number.POSITIVE_INFINITY,
                              ease: "easeInOut",
                            }}
                          />
                        )}
                      </Link>
                    </motion.div>
                  );
                })}
              </div>

              {/* Enhanced Logout Button */}
              {token && (
                <motion.div
                  whileHover={{ scale: 1.05 }}
                  whileTap={{ scale: 0.95 }}
                  className="ml-4"
                >
                  <button
                    onClick={handleLogout}
                    className="flex items-center space-x-2 px-6 py-3 text-sm font-semibold text-red-300 hover:text-white bg-red-500/10 hover:bg-red-500/20 border border-red-500/30 hover:border-red-400/50 rounded-full transition-all duration-300 backdrop-blur-xl shadow-lg shadow-red-500/20 hover:shadow-red-500/30"
                  >
                    <LogOut className="w-4 h-4" />
                    <span>Logout</span>
                  </button>
                </motion.div>
              )}
            </div>

            {/* Enhanced Mobile Menu Button */}
            <div className="lg:hidden">
              <motion.button
                whileTap={{ scale: 0.9 }}
                onClick={() => setIsOpen(!isOpen)}
                className="relative p-4 bg-[#022639]/50 backdrop-blur-xl border border-[#42D5AE]/50 rounded-2xl hover:bg-[#022639]/70 transition-all duration-300 shadow-xl shadow-[#42D5AE]/20"
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
                      <X className="w-6 h-6 text-white" />
                    </motion.div>
                  ) : (
                    <motion.div
                      key="menu"
                      initial={{ rotate: 90, opacity: 0 }}
                      animate={{ rotate: 0, opacity: 1 }}
                      exit={{ rotate: -90, opacity: 0 }}
                      transition={{ duration: 0.2 }}
                    >
                      <Menu className="w-6 h-6 text-white" />
                    </motion.div>
                  )}
                </AnimatePresence>
              </motion.button>
            </div>
          </div>
        </div>
      </motion.nav>

      {/* Enhanced Mobile Menu */}
      <AnimatePresence>
        {isOpen && (
          <>
            {/* Backdrop */}
            <motion.div
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
              transition={{ duration: 0.3 }}
              className="fixed inset-0 z-40 bg-[#022639]/40 backdrop-blur-md"
              onClick={closeMenu}
            />

            {/* Mobile menu panel */}
            <motion.div
              initial={{ opacity: 0, scale: 0.9, y: -20 }}
              animate={{ opacity: 1, scale: 1, y: 0 }}
              exit={{ opacity: 0, scale: 0.9, y: -20 }}
              transition={{ duration: 0.4, ease: [0.16, 1, 0.3, 1] }}
              className="fixed top-28 left-4 right-4 z-50 bg-[#022639]/50 backdrop-blur-3xl border border-[#42D5AE]/40 rounded-3xl shadow-2xl overflow-hidden"
            >
              {/* Animated top border */}
              <motion.div
                className="absolute top-0 left-0 w-full h-1 bg-gradient-to-r from-[#42D5AE] via-[#38b28d] to-[#42D5AE]"
                animate={{
                  background: [
                    "linear-gradient(90deg, #42D5AE, #38b28d, #42D5AE)",
                    "linear-gradient(90deg, #38b28d, #42D5AE, #38b28d)",
                    "linear-gradient(90deg, #42D5AE, #38b28d, #42D5AE)",
                  ],
                }}
                transition={{ duration: 3, repeat: Number.POSITIVE_INFINITY }}
              />

              {/* Background effects */}
              <div className="absolute inset-0">
                <motion.div
                  className="absolute top-8 right-8 w-32 h-32 bg-gradient-to-br from-[#42D5AE]/20 to-[#38b28d]/20 rounded-full blur-3xl"
                  animate={{
                    scale: [1, 1.4, 1],
                    opacity: [0.3, 0.7, 0.3],
                  }}
                  transition={{ duration: 5, repeat: Number.POSITIVE_INFINITY }}
                />
              </div>

              <div className="relative z-10 p-8 space-y-3">
                {filteredLinks.map(({ label, path }, index) => {
                  const isActive =
                    pathname === path ||
                    (label === "Sessions" && pathname === "/sessions");
                  const linkPath = label === "Sessions" ? "/sessions" : path;

                  return (
                    <motion.div
                      key={label}
                      initial={{ opacity: 0, x: -30 }}
                      animate={{ opacity: 1, x: 0 }}
                      transition={{ delay: index * 0.08, duration: 0.4 }}
                    >
                      <Link
                        to={linkPath}
                        className={`block px-8 py-5 text-lg font-semibold transition-all duration-300 rounded-2xl ${
                          isActive
                            ? "text-white bg-gradient-to-r from-[#42D5AE] via-[#38b28d] to-[#42D5AE] shadow-xl shadow-[#42D5AE]/40"
                            : "text-gray-300 hover:text-white hover:bg-[#022639]/60"
                        }`}
                        onClick={closeMenu}
                      >
                        <span className="relative z-10">{label}</span>
                      </Link>
                    </motion.div>
                  );
                })}

                {/* Mobile Logout Button */}
                {token && (
                  <motion.div
                    initial={{ opacity: 0, x: -30 }}
                    animate={{ opacity: 1, x: 0 }}
                    transition={{
                      delay: filteredLinks.length * 0.08,
                      duration: 0.4,
                    }}
                    className="pt-6 border-t border-[#42D5AE]/30"
                  >
                    <button
                      onClick={handleLogout}
                      className="w-full flex items-center space-x-3 px-8 py-5 text-lg font-semibold text-red-300 hover:text-white bg-red-500/10 hover:bg-red-500/20 border border-red-500/30 hover:border-red-400/50 rounded-2xl transition-all duration-300"
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
      <div className="h-28" />
    </>
  );
}

// Utility function for throttling
function throttle<T extends (...args: any[]) => any>(
  func: T,
  limit: number
): T {
  let inThrottle: boolean;
  return ((...args: any[]) => {
    if (!inThrottle) {
      func.apply(this, args);
      inThrottle = true;
      setTimeout(() => (inThrottle = false), limit);
    }
  }) as T;
}
