import { useState, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { Menu, X, User, LogOut, ChevronDown, Zap } from "lucide-react";
import { Link, useLocation } from "react-router-dom";
import { NavLinks } from "../../data/data";
import { CookiesService } from "../../imports";

export default function Navbar() {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [isScrolled, setIsScrolled] = useState(false);
  const [token, setToken] = useState<string | null>(null);
  const [showUserMenu, setShowUserMenu] = useState(false);
  const pathname = useLocation().pathname;

  // Handle scroll effect
  useEffect(() => {
    const handleScroll = () => setIsScrolled(window.scrollY > 20);
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  // Get token
  useEffect(() => {
    setToken(CookiesService.get("UserToken"));
  }, []);

  const handleLogout = () => {
    CookiesService.remove("UserToken");
    setToken(null);
    setShowUserMenu(false);
  };

  return (
    <>
      {/* Navbar */}
      <motion.nav
        initial={{ y: -100, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ duration: 0.6, ease: "easeOut" }}
        className={`fixed top-0 left-0 right-0 z-50 transition-all duration-500 ${
          isScrolled
            ? "bg-white/95 backdrop-blur-xl border-b border-gray-200/50 shadow-lg"
            : "bg-white/80 backdrop-blur-md"
        }`}
      >
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            {/* Logo */}
            <motion.div
              whileHover={{ scale: 1.02 }}
              className="flex items-center gap-4"
            >
              <Link to="/" className="flex items-center group">
                <div className="ml-3  sm:block">
                  <span className="text-xl font-black bg-gradient-to-r from-[#022639] to-[#42D5AE] bg-clip-text text-transparent">
                    TechPractica
                  </span>
                </div>
              </Link>
            </motion.div>

            {/* Desktop Nav */}
            <div className="hidden md:flex items-center space-x-1">
              {NavLinks.slice(0, 4).map(({ label, path, icon: Icon }) => {
                const isActive = pathname === path;
                return (
                  <motion.div
                    key={label}
                    whileHover={{ scale: 1.05 }}
                    whileTap={{ scale: 0.95 }}
                  >
                    <Link
                      to={path}
                      className={`relative flex items-center gap-2 px-4 py-2 rounded-xl text-sm font-medium transition-all duration-300 ${
                        isActive
                          ? "text-[#42D5AE]"
                          : "text-gray-600 hover:text-[#42D5AE] hover:bg-gray-50"
                      }`}
                    >
                      <Icon className="w-4 h-4" />
                      {label}
                      {isActive && (
                        <motion.div
                          layoutId="activeTab"
                          className="absolute inset-0 rounded-xl border border-[#42D5AE]/20"
                          initial={false}
                          animate={{ opacity: 1 }}
                          exit={{ opacity: 0 }}
                          transition={{
                            type: "spring",
                            stiffness: 500,
                            damping: 30,
                          }}
                        />
                      )}
                    </Link>
                  </motion.div>
                );
              })}
            </div>

            {/* Right Section */}
            <div className="flex items-center gap-3">
              {/* User / Auth */}
              {token ? (
                <div className="relative">
                  <motion.button
                    whileHover={{ scale: 1.05 }}
                    whileTap={{ scale: 0.95 }}
                    onClick={() => setShowUserMenu(!showUserMenu)}
                    className="flex items-center gap-2 p-2 rounded-xl hover:bg-gray-100 transition-colors"
                  >
                    <div className="w-8 h-8 rounded-lg bg-gradient-to-br from-[#42D5AE] to-[#38b28d] flex items-center justify-center text-white font-semibold text-sm">
                      JD
                    </div>
                    <ChevronDown
                      className={`w-4 h-4 text-gray-600 transition-transform ${
                        showUserMenu ? "rotate-180" : ""
                      }`}
                    />
                  </motion.button>

                  <AnimatePresence>
                    {showUserMenu && (
                      <>
                        {/* Backdrop */}
                        <motion.div
                          initial={{ opacity: 0 }}
                          animate={{ opacity: 1 }}
                          exit={{ opacity: 0 }}
                          className="fixed inset-0 z-10"
                          onClick={() => setShowUserMenu(false)}
                        />

                        {/* Menu */}
                        <motion.div
                          initial={{ opacity: 0, scale: 0.95, y: -10 }}
                          animate={{ opacity: 1, scale: 1, y: 0 }}
                          exit={{ opacity: 0, scale: 0.95, y: -10 }}
                          className="absolute right-0 top-full mt-2 w-56 bg-white border border-gray-200 rounded-2xl shadow-xl z-20 py-2"
                        >
                          <div className="px-4 py-3 border-b border-gray-100">
                            <p className="font-semibold text-gray-900">
                              John Doe
                            </p>
                            <p className="text-sm text-gray-600">
                              john@example.com
                            </p>
                          </div>
                          <div className="py-2">
                            <Link
                              to="/profile"
                              className="flex items-center gap-3 px-4 py-2 text-sm hover:bg-gray-50 transition-colors"
                              onClick={() => setShowUserMenu(false)}
                            >
                              <User className="w-4 h-4" />
                              Profile
                            </Link>
                          </div>
                          <div className="border-t border-gray-100 py-2">
                            <button
                              onClick={handleLogout}
                              className="flex items-center gap-3 px-4 py-2 text-sm text-red-600 hover:bg-red-50 transition-colors w-full text-left"
                            >
                              <LogOut className="w-4 h-4" />
                              Logout
                            </button>
                          </div>
                        </motion.div>
                      </>
                    )}
                  </AnimatePresence>
                </div>
              ) : (
                <div className="hidden sm:flex items-center gap-2">
                  <Link
                    to="/auth"
                    className="px-4 py-2 text-sm font-medium text-gray-600 hover:text-[#42D5AE] transition-colors"
                  >
                    Login
                  </Link>
                  <Link
                    to="/auth"
                    className="px-4 py-2 text-sm font-medium bg-gradient-to-r from-[#42D5AE] to-[#38b28d] text-white rounded-xl hover:shadow-lg transition-all duration-300"
                  >
                    Sign Up
                  </Link>
                </div>
              )}

              {/* Mobile Menu Button */}
              <motion.button
                whileTap={{ scale: 0.9 }}
                onClick={() => setIsSidebarOpen(!isSidebarOpen)}
                className="lg:hidden p-2 rounded-xl hover:bg-gray-100 transition-colors"
              >
                <AnimatePresence>
                  {isSidebarOpen ? (
                    <motion.div
                      key="close"
                      initial={{ rotate: -90, opacity: 0 }}
                      animate={{ rotate: 0, opacity: 1 }}
                      exit={{ rotate: 90, opacity: 0 }}
                      transition={{ duration: 0.2 }}
                    >
                      <X className="w-5 h-5 text-gray-600" />
                    </motion.div>
                  ) : (
                    <motion.div
                      key="menu"
                      initial={{ rotate: 90, opacity: 0 }}
                      animate={{ rotate: 0, opacity: 1 }}
                      exit={{ rotate: -90, opacity: 0 }}
                      transition={{ duration: 0.2 }}
                    >
                      <Menu className="w-5 h-5 text-gray-600" />
                    </motion.div>
                  )}
                </AnimatePresence>
              </motion.button>
            </div>
          </div>
        </div>
      </motion.nav>

      {/* Mobile Sidebar */}
      <AnimatePresence>
        {isSidebarOpen && (
          <>
            {/* Backdrop */}
            <motion.div
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
              transition={{ duration: 0.3 }}
              className="fixed inset-0 z-40 bg-black/20 backdrop-blur-sm lg:hidden"
              onClick={() => setIsSidebarOpen(false)}
            />

            {/* Sidebar */}
            <motion.div
              initial={{ x: "100%", opacity: 0 }}
              animate={{ x: 0, opacity: 1 }}
              exit={{ x: "100%", opacity: 0 }}
              transition={{ type: "spring", stiffness: 300, damping: 30 }}
              className="fixed top-0 right-0 bottom-0 z-50 w-80 bg-white shadow-2xl lg:hidden"
            >
              <div className="flex items-center justify-between p-6 border-b border-gray-200">
                <div className="flex items-center gap-3">
                  <span className="text-xl font-black bg-gradient-to-r from-[#022639] to-[#42D5AE] bg-clip-text text-transparent">
                    TechPractica
                  </span>
                </div>
                <button
                  onClick={() => setIsSidebarOpen(false)}
                  className="p-2 rounded-xl hover:bg-gray-100 transition-colors"
                >
                  <X className="w-5 h-5 text-gray-600" />
                </button>
              </div>

              <div className="flex-1 overflow-y-auto py-6">
                <div className="space-y-2 px-6">
                  {NavLinks.map(({ label, path, icon: Icon }, index) => {
                    const isActive = pathname === path;
                    return (
                      <motion.div
                        key={label}
                        initial={{ opacity: 0, x: 20 }}
                        animate={{ opacity: 1, x: 0 }}
                        transition={{ delay: index * 0.05, duration: 0.3 }}
                      >
                        <Link
                          to={path}
                          className={`flex items-center gap-3 px-4 py-3 rounded-xl text-base font-medium transition-all duration-300 ${
                            isActive
                              ? "text-[#42D5AE] bg-[#42D5AE]/10 border border-[#42D5AE]/20"
                              : "text-gray-700 hover:text-[#42D5AE] hover:bg-gray-50"
                          }`}
                          onClick={() => setIsSidebarOpen(false)}
                        >
                          <Icon className="w-5 h-5" />
                          {label}
                        </Link>
                      </motion.div>
                    );
                  })}
                </div>

                {/* Auth / Logout */}
                {!token && (
                  <div className="mt-8 px-6 space-y-3">
                    <Link
                      to="/auth"
                      className="block w-full text-center px-4 py-3 border border-gray-300 text-gray-700 rounded-xl hover:bg-gray-50 transition-colors font-medium"
                      onClick={() => setIsSidebarOpen(false)}
                    >
                      Login
                    </Link>
                    <Link
                      to="/auth"
                      className="block w-full text-center px-4 py-3 bg-gradient-to-r from-[#42D5AE] to-[#38b28d] text-white rounded-xl hover:shadow-lg transition-all duration-300 font-medium"
                      onClick={() => setIsSidebarOpen(false)}
                    >
                      Sign Up
                    </Link>
                  </div>
                )}

                {token && (
                  <div className="mt-8 px-6">
                    <button
                      onClick={() => {
                        handleLogout();
                        setIsSidebarOpen(false);
                      }}
                      className="flex items-center gap-3 w-full px-4 py-3 text-red-600 hover:bg-red-50 rounded-xl transition-colors font-medium"
                    >
                      <LogOut className="w-5 h-5" />
                      Logout
                    </button>
                  </div>
                )}
              </div>
            </motion.div>
          </>
        )}
      </AnimatePresence>

      {/* Spacer */}
      <div className="h-16" />
    </>
  );
}
