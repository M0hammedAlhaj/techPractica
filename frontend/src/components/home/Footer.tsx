import { motion } from "framer-motion";

const Footer = () => {
  return (
    <footer className="relative bg-gradient-to-br from-[#42D5AE] via-[#38b28d] to-[#022639] text-white w-full mt-auto overflow-hidden">
      {/* Enhanced Background Animation */}
      <div className="absolute inset-0 overflow-hidden">
        <motion.div
          className="absolute inset-0"
          animate={{
            background: [
              "radial-gradient(circle at 20% 50%, rgba(255,255,255,0.15) 0%, transparent 50%)",
              "radial-gradient(circle at 80% 50%, rgba(255,255,255,0.15) 0%, transparent 50%)",
              "radial-gradient(circle at 50% 20%, rgba(255,255,255,0.15) 0%, transparent 50%)",
              "radial-gradient(circle at 20% 50%, rgba(255,255,255,0.15) 0%, transparent 50%)",
            ],
          }}
          transition={{
            duration: 25,
            repeat: Number.POSITIVE_INFINITY,
            ease: "easeInOut",
          }}
        />

        {/* Floating orbs */}
        <motion.div
          className="absolute -top-20 -left-20 w-80 h-80 bg-white/10 rounded-full blur-3xl"
          animate={{
            x: [0, 50, 0],
            y: [0, -30, 0],
            scale: [1, 1.2, 1],
          }}
          transition={{
            duration: 20,
            repeat: Number.POSITIVE_INFINITY,
            ease: "easeInOut",
          }}
        />
        <motion.div
          className="absolute -bottom-20 -right-20 w-96 h-96 bg-white/10 rounded-full blur-3xl"
          animate={{
            x: [0, -40, 0],
            y: [0, 20, 0],
            scale: [1, 0.8, 1],
          }}
          transition={{
            duration: 25,
            repeat: Number.POSITIVE_INFINITY,
            ease: "easeInOut",
          }}
        />

        {/* Geometric overlays */}
        <motion.div
          className="absolute top-20 left-20 w-32 h-32 border-2 border-white/20 rounded-full"
          animate={{ rotate: 360 }}
          transition={{
            duration: 40,
            repeat: Number.POSITIVE_INFINITY,
            ease: "linear",
          }}
        />
        <motion.div
          className="absolute bottom-20 right-20 w-24 h-24 border-2 border-white/20 rounded-lg"
          animate={{ rotate: -360 }}
          transition={{
            duration: 35,
            repeat: Number.POSITIVE_INFINITY,
            ease: "linear",
          }}
        />
      </div>

      <div className="relative z-10 max-w-5xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* CTA Section */}
        {/* <div className="text-center py-24">
          <motion.div
            initial={{ opacity: 0, y: 30 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.8 }}
          >
            <h2 className="text-5xl md:text-7xl font-black text-white mb-8 tracking-tight leading-tight">
              Ready to Start Your{" "}
              <span className="bg-gradient-to-r from-white to-[#42D5AE] bg-clip-text text-transparent">
                Journey?
              </span>
            </h2>
            <p className="text-2xl text-white/90 mb-12 max-w-3xl mx-auto font-medium leading-relaxed">
              Join TechPractica today and transform your career with hands-on
              learning, expert guidance, and a supportive community of
              developers.
            </p>
            <div className="flex flex-col sm:flex-row gap-6 justify-center mb-16">
              <motion.button
                whileHover={{
                  scale: 1.05,
                  boxShadow: "0 20px 40px rgba(0,0,0,0.2)",
                }}
                whileTap={{ scale: 0.95 }}
                className="bg-white text-[#022639] hover:bg-gray-50 px-12 py-5 text-xl rounded-xl font-bold transition-all duration-300 flex items-center gap-3 justify-center shadow-2xl"
              >
                Start Free Trial
                <ArrowRight className="w-6 h-6 transition-transform group-hover:translate-x-1" />
              </motion.button>
              <motion.button
                whileHover={{
                  scale: 1.05,
                  backgroundColor: "rgba(255,255,255,0.1)",
                }}
                whileTap={{ scale: 0.95 }}
                className="border-2 border-white text-white hover:bg-white/10 px-12 py-5 text-xl bg-transparent rounded-xl font-bold transition-all duration-300 backdrop-blur-sm flex items-center gap-3 justify-center"
              >
                <Play className="w-6 h-6" />
                View Pricing
              </motion.button>
            </div>
          </motion.div>
        </div> */}

        {/* Divider */}
        <motion.div
          className="w-full h-px bg-gradient-to-r from-transparent via-white/30 to-transparent mb-12"
          initial={{ scaleX: 0 }}
          whileInView={{ scaleX: 1 }}
          viewport={{ once: true }}
          transition={{ duration: 1 }}
        />

        {/* Footer Content */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          transition={{ duration: 0.8, delay: 0.2 }}
          className="flex flex-col sm:flex-row justify-between items-center gap-8 pb-12"
        >
          {/* Logo Section */}
          <motion.div
            whileHover={{ scale: 1.05 }}
            transition={{ duration: 0.3 }}
            className="mb-4 sm:mb-0 relative group"
          >
            {/* Glow effect behind logo */}
            <motion.div
              className="absolute inset-0 bg-gradient-to-r from-white/30 to-white/20 rounded-2xl blur-xl opacity-0 group-hover:opacity-100 transition-opacity duration-500"
              animate={{
                scale: [1, 1.1, 1],
              }}
              transition={{
                duration: 4,
                repeat: Number.POSITIVE_INFINITY,
                ease: "easeInOut",
              }}
            />

            {/* Logo container with enhanced styling */}
            <div className="relative bg-gradient-to-br from-white/20 to-white/10 backdrop-blur-sm border border-white/30 rounded-2xl p-4 shadow-2xl shadow-black/20 group-hover:border-white/50 transition-all duration-500">
              <img
                src="/src/assets/white.png"
                className="h-20 w-auto filter drop-shadow-lg group-hover:drop-shadow-2xl transition-all duration-300"
                alt="TechPractica Logo"
              />

              {/* Animated border */}
              <motion.div
                className="absolute inset-0 rounded-2xl border-2 border-white/50 opacity-0 group-hover:opacity-100"
                animate={{
                  rotate: [0, 360],
                }}
                transition={{
                  duration: 8,
                  repeat: Number.POSITIVE_INFINITY,
                  ease: "linear",
                }}
              />
            </div>
          </motion.div>

          {/* Copyright Section */}
          <motion.div
            initial={{ opacity: 0, x: 20 }}
            whileInView={{ opacity: 1, x: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.8, delay: 0.4 }}
            className="text-center sm:text-right"
          >
            <div className="relative">
              {/* Background glow */}
              <div className="absolute inset-0 bg-gradient-to-r from-transparent via-white/10 to-transparent rounded-lg blur-sm" />

              {/* Copyright text with enhanced styling */}
              <p className="relative text-white/90 text-sm font-medium px-6 py-3 bg-gradient-to-r from-transparent via-white/10 to-transparent backdrop-blur-sm border border-white/20 rounded-lg shadow-lg">
                © {new Date().getFullYear()} TechPractica. All rights reserved.
              </p>

              {/* Subtle animated underline */}
              <motion.div
                className="absolute bottom-0 left-1/2 transform -translate-x-1/2 h-0.5 bg-gradient-to-r from-transparent via-white to-transparent"
                initial={{ width: 0 }}
                whileInView={{ width: "80%" }}
                viewport={{ once: true }}
                transition={{ duration: 1, delay: 0.6 }}
              />
            </div>
          </motion.div>
        </motion.div>
      </div>

      {/* Bottom gradient line */}
      <motion.div
        className="absolute bottom-0 left-0 right-0 h-1 bg-gradient-to-r from-transparent via-white/50 to-transparent"
        animate={{
          opacity: [0.5, 1, 0.5],
        }}
        transition={{
          duration: 2,
          repeat: Number.POSITIVE_INFINITY,
          ease: "easeInOut",
        }}
      />
    </footer>
  );
};

export default Footer;
