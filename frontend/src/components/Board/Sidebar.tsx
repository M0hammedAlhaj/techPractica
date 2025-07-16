import { motion } from "framer-motion";
import { FaCommentDots, FaUserFriends, FaPlus } from "react-icons/fa";
import { MdOutlineSpaceDashboard, MdOutlineViewKanban } from "react-icons/md";
import userImgg from "../../assets/user.png";

const NavigationBar = () => {
  return (
    <motion.div
      initial={{ y: -20, opacity: 0 }}
      animate={{ y: 0, opacity: 1 }}
      transition={{ duration: 0.3 }}
      className="sticky top-0 left-0 right-0 h-14 bg-[#022639] text-gray-200 flex items-center z-50 shadow-lg mb-7 border-b border-[#0a344f]"
    >
      {/* User info */}
      <div className="flex items-center gap-2 sm:gap-3 px-4 sm:px-5 shrink-0 min-w-max border-r border-[#0a344f] h-full">
        <motion.div
          whileHover={{ scale: 1.05 }}
          className="w-8 h-8 rounded-full overflow-hidden flex items-center justify-center border-2 border-[#38b28d]"
        >
          <img
            src={userImgg}
            alt="User profile"
            className="w-full h-full object-cover"
          />
        </motion.div>
        <div className="overflow-hidden">
          <div className="font-medium leading-tight whitespace-nowrap text-sm">
            Owner Username
          </div>
          <div className="text-xs text-gray-400 whitespace-nowrap truncate max-w-[120px] sm:max-w-[160px]">
            Session Name
          </div>
        </div>
      </div>

      {/* Navigation */}
      <nav className="flex-1 flex items-center h-full overflow-x-auto scrollbar-hide">
        <div className="flex items-center space-x-1 px-2 h-full">
          {[
            {
              icon: <MdOutlineSpaceDashboard className="text-lg" />,
              label: "Dashboard",
            },
            {
              icon: <MdOutlineViewKanban className="text-lg" />,
              label: "Kanban",
            },
            { icon: <FaCommentDots className="text-lg" />, label: "Chat" },
            {
              icon: (
                <>
                  <FaUserFriends className="text-lg" />
                  <FaPlus className="text-xs opacity-60 ml-1" />
                </>
              ),
              label: "Members",
            },
          ].map((item, index) => (
            <motion.button
              key={index}
              whileHover={{ backgroundColor: "rgba(56, 178, 141, 0.2)" }}
              whileTap={{ scale: 0.98 }}
              className="flex items-center gap-2 h-full px-3 sm:px-4 rounded-md transition-all whitespace-nowrap text-sm font-medium relative group"
            >
              {item.icon}
              <span>{item.label}</span>
              <motion.span
                className="absolute bottom-0 left-0 right-0 h-0.5 bg-[#42D5AE]"
                initial={{ scaleX: 0 }}
                whileHover={{ scaleX: 1 }}
                transition={{ duration: 0.2 }}
              />
            </motion.button>
          ))}
        </div>
      </nav>

      {/* Avatar Group */}
      <div className="flex items-center gap-1 pr-4 -space-x-4">
        {["AA", "MN", "M", "MA"].map((initials, idx) => (
          <div
            key={idx}
            className="w-8 h-8 rounded-full bg-[#42D5AE] hover:bg-[#38b28d] text-xs font-semibold flex items-center justify-center border-2 border-white "
          >
            {initials}
          </div>
        ))}
      </div>

      <div className="absolute right-0 top-0 bottom-0 w-6 bg-gradient-to-l from-[#022639] pointer-events-none" />
    </motion.div>
  );
};

export default NavigationBar;
