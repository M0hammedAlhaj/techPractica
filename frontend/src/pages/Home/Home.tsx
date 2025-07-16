import { motion } from "framer-motion";
import { categories, features } from "../../data/data.ts";
import { useNavigate } from "react-router-dom";
import { CookiesService } from "../../imports.ts";

const HomePage = () => {
  document.title = "TechPractica | Home";
  const navigate = useNavigate();
  const token = CookiesService.get("UserToken");
  const txt = token ? "Start Learning" : "Get Started";

  // Animation variants
  const container = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.1,
        when: "beforeChildren",
      },
    },
  };

  const fadeInUp = {
    hidden: { opacity: 0, y: 20 },
    visible: {
      opacity: 1,
      y: 0,
      transition: {
        duration: 0.6,
        ease: [0.25, 0.1, 0.25, 1],
      },
    },
  };

  const fadeInScale = {
    hidden: { opacity: 0, scale: 0.95 },
    visible: {
      opacity: 1,
      scale: 1,
      transition: {
        duration: 0.5,
        ease: "backOut",
      },
    },
  };

  return (
    <div className="overflow-hidden">
      {/* HERO SECTION */}
      <section className="relative bg-gradient-to-br from-[#f0fdf9] via-[#f8fafc] to-[#e0f2fe] h-[700px] flex items-center justify-center px-6">
        <div className="absolute inset-0 overflow-hidden">
          <motion.img
            src="/src/assets/left-side.png"
            alt="Tech Logos"
            className="absolute left-0 top-0 h-full opacity-80 lg:opacity-100 object-cover hidden lg:block filter brightness-105 contrast-105"
            initial={{ x: -50, opacity: 0 }}
            animate={{ x: 0, opacity: 0.8 }}
            transition={{ duration: 0.8, ease: "easeOut" }}
          />

          <motion.img
            src="/src/assets/right-side.png"
            alt="Tech Logos"
            className="absolute right-0 top-0 h-full opacity-80 lg:opacity-100 object-cover hidden lg:block filter brightness-105 contrast-105"
            initial={{ x: 50, opacity: 0 }}
            animate={{ x: 0, opacity: 0.8 }}
            transition={{ duration: 0.8, ease: "easeOut" }}
          />
          <motion.div
            className="absolute inset-0 bg-gradient-to-r from-white/60 via-transparent to-white/60"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ delay: 0.5, duration: 0.8 }}
          />
        </div>

        <motion.div
          initial="hidden"
          animate="visible"
          variants={container}
          className="text-center max-w-2xl z-10 px-4"
        >
          {!token && (
            <motion.div
              variants={fadeInUp}
              className="mb-6 inline-flex items-center rounded-full border border-gray-200 px-4 py-2 text-sm font-medium text-gray-700 bg-white/90 backdrop-blur-md shadow-sm hover:shadow-md transition-all"
            >
              Start Your Tech Journey Today
            </motion.div>
          )}
          <motion.h1
            variants={fadeInUp}
            className="text-5xl md:text-6xl font-bold text-gray-900 mb-6 leading-tight"
          >
            Turn Knowledge <span className="text-[#42D5AE]">into Action</span>
          </motion.h1>
          <motion.div
            variants={fadeInUp}
            className="w-32 h-1.5 bg-gradient-to-r from-[#42D5AE] to-[#022639] mx-auto rounded-full mb-8"
          />
          <motion.p
            variants={fadeInUp}
            className="mt-6 text-gray-700 text-xl leading-relaxed max-w-2xl mx-auto"
          >
            Build real-world projects and apply your technical skills in our
            immersive, hands-on learning environment.
          </motion.p>
          <motion.div
            variants={fadeInUp}
            className="mt-12 flex justify-center gap-4"
          >
            <motion.button
              whileHover={{
                y: -3,
                boxShadow: "0 10px 15px -3px rgba(0,0,0,0.1)",
              }}
              whileTap={{ scale: 0.98 }}
              onClick={() => navigate(token ? "/Learn" : "/User")}
              className="bg-gradient-to-r from-[#42D5AE] to-[#38b28d] text-white font-medium px-8 py-3.5 rounded-lg shadow-lg transition-all"
            >
              {txt}
            </motion.button>
          </motion.div>
        </motion.div>
      </section>

      {/* CATEGORIES SECTION */}
      <section className="bg-[#f8fafc] py-20">
        <div className="max-w-7xl mx-auto px-6 sm:px-8 lg:px-12">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true, margin: "-100px" }}
            transition={{ duration: 0.6 }}
            className="text-center mb-16"
          >
            <h2 className="text-4xl md:text-5xl font-bold text-gray-900 mb-4">
              Explore Categories
            </h2>
            <motion.div
              initial={{ scaleX: 0 }}
              whileInView={{ scaleX: 1 }}
              viewport={{ once: true }}
              transition={{ delay: 0.3, duration: 0.8 }}
              className="w-44 h-1.5 bg-gradient-to-r from-[#42D5AE] to-[#022639] mx-auto mt-6 rounded-full transform origin-left"
            />
          </motion.div>

          <motion.div
            initial="hidden"
            whileInView="visible"
            viewport={{ once: true, margin: "-50px" }}
            variants={container}
            className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-6 max-w-6xl mx-auto"
          >
            {categories.map(({ Icon, title, style }, idx) => (
              <motion.div
                key={idx}
                variants={fadeInScale}
                whileHover={{ y: -5, scale: 1.02 }}
                whileTap={{ scale: 0.98 }}
                transition={{ duration: 0.3 }}
                className="group flex flex-col items-center p-6 cursor-pointer "
                onClick={() => navigate(`/Learn/${title}`)}
              >
                <div className="p-4 rounded-full mb-4 group-hover:bg-[#42D5AE]/10 transition-colors">
                  <Icon className={`${style} text-xl`} />
                </div>
                <h3 className="text-base font-semibold text-gray-800">
                  {title}
                </h3>
              </motion.div>
            ))}
          </motion.div>
        </div>
      </section>

      {/* FEATURES SECTION */}
      <section className="py-24 bg-gradient-to-b from-white to-[#f8fafc]">
        <div className="max-w-7xl mx-auto px-6 sm:px-8 lg:px-12">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true, margin: "-100px" }}
            transition={{ duration: 0.6 }}
            className="text-center mb-20"
          >
            <h2 className="text-4xl md:text-5xl font-bold text-gray-900 mb-4">
              Why Choose Us?
            </h2>
            <motion.div
              initial={{ scaleX: 0 }}
              whileInView={{ scaleX: 1 }}
              viewport={{ once: true }}
              transition={{ delay: 0.3, duration: 0.8 }}
              className="w-44 h-1.5 bg-gradient-to-r from-[#42D5AE] to-[#022639] mx-auto mt-6 rounded-full transform origin-left"
            />
          </motion.div>

          <motion.div
            initial="hidden"
            whileInView="visible"
            viewport={{ once: true, margin: "-50px" }}
            variants={container}
            className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8"
          >
            {features.map(({ Icon, title, description, style }, idx) => (
              <motion.div
                key={idx}
                variants={fadeInScale}
                whileHover={{
                  y: -5,
                  boxShadow: "0 10px 25px -5px rgba(0,0,0,0.1)",
                }}
                whileTap={{ scale: 0.98 }}
                className="bg-white rounded-xl p-8 border border-gray-100"
              >
                <div className="bg-[#42D5AE]/10 p-3 rounded-full w-14 h-14 flex items-center justify-center mb-6 mx-auto">
                  <Icon className={`${style} text-xl`} />
                </div>
                <h3 className="text-lg font-semibold text-gray-800 text-center mb-3">
                  {title}
                </h3>
                <p className="text-gray-600 text-center text-sm leading-relaxed">
                  {description}
                </p>
              </motion.div>
            ))}
          </motion.div>
        </div>
      </section>
    </div>
  );
};

export default HomePage;
