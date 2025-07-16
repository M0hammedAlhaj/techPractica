import { useState } from "react";
import { Button, CookiesService, ErrorMsg, Inputs } from "../../imports";
import { BiHide, BiShow } from "react-icons/bi";
import { SubmitHandler, useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import { loginSchema } from "../../validation";
import { Link, useNavigate } from "react-router-dom";
import axiosInstance from "../../config/axios.config";
import toast from "react-hot-toast";
import { AxiosError } from "axios";
import { LoginForm } from "../../interfaces";
import { FiLoader, FiLogIn } from "react-icons/fi";
import { FaGoogle, FaGithub } from "react-icons/fa";
const Login = () => {
  document.title = "TechPractica | Login";

  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(loginSchema),
  });
  type IFormInput = {
    userEmail: string;
    userPassword: string;
  };
  const onSubmit: SubmitHandler<IFormInput> = async (data) => {
    try {
      setIsSubmitting(true);
      const response = await axiosInstance.post("/authenticated/login", data);
      toast.success("Login successful!", { position: "top-center" });
      CookiesService.set("UserToken", response.data);

      navigate("/");
    } catch (error) {
      const err = error as AxiosError<{ message: string }>;
      toast.error(err.response?.data.message || "Login failed", {
        position: "top-center",
        duration: 4000,
      });
    } finally {
      setIsSubmitting(false);
    }
  };

  const renderForm = LoginForm.map(({ label, name, placeholder, type }) => {
    const isPasswordField = type === "password";

    return (
      <div key={name} className="space-y-2">
        <label
          htmlFor={label}
          className="block text-sm font-medium text-gray-700"
        >
          {label}
        </label>
        <div className="relative">
          <Inputs
            id={label}
            type={
              type === "password" ? (showPassword ? "text" : "password") : type
            }
            placeholder={placeholder}
            {...register(name)}
            className="w-full px-4 py-3 text-base border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#42D5AE] focus:border-transparent transition-colors duration-200"
          />
          {isPasswordField && (
            <button
              type="button"
              onClick={() => setShowPassword(!showPassword)}
              className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 hover:text-[#42D5AE] transition-colors"
            >
              {showPassword ? (
                <BiHide className="w-5 h-5" />
              ) : (
                <BiShow className="w-5 h-5" />
              )}
            </button>
          )}
        </div>
        {errors[name] && <ErrorMsg Msg={errors[name]?.message} />}
      </div>
    );
  });

  return (
    <div className="bg-white rounded-xl shadow-lg p-8 w-full max-w-md mx-auto border border-gray-100">
      <div className="text-center mb-8">
        <h1 className="text-2xl font-bold text-gray-900">Welcome Back</h1>
        <p className="text-gray-500 mt-2">
          Sign in to continue to your account
        </p>
      </div>

      <form onSubmit={handleSubmit(onSubmit)} className="space-y-5">
        {renderForm}

        <div className="flex items-center justify-between">
          <Link
            to="/User/ResetPassword"
            className="text-sm font-medium text-emerald-600 hover:text-emerald-700 focus:outline-none focus:underline"
          >
            Forgot password?
          </Link>
        </div>

        <Button
          type="submit"
          disabled={isSubmitting}
          className="w-full py-3 px-4  bg-[#42D5AE] hover:bg-[#38b28d] text-white font-medium rounded-lg shadow-sm transition-all duration-300 flex items-center justify-center focus:ring-2 focus:ring-emerald-500 focus:ring-offset-2"
          aria-label={isSubmitting ? "Signing in..." : "Sign in"}
        >
          {isSubmitting ? (
            <>
              <FiLoader className="animate-spin -ml-1 mr-3 h-5 w-5 text-white" />
              Signing in...
            </>
          ) : (
            <>
              <FiLogIn className="-ml-1 mr-3 h-5 w-5" />
              Sign In
            </>
          )}
        </Button>

        <div className="relative my-6">
          <div className="absolute inset-0 flex items-center">
            <div className="w-full border-t border-gray-300"></div>
          </div>
          <div className="relative flex justify-center text-sm">
            <span className="px-2 bg-white text-gray-500">Or sign in with</span>
          </div>
        </div>

        <div className="flex justify-center gap-4">
          <button
            type="button"
            className="p-3 rounded-full border border-gray-300 hover:bg-gray-50 transition-colors shadow-sm  "
            aria-label="Sign up with Google"
          >
            <FaGoogle className="h-5 w-5 text-black" />
          </button>

          <button
            type="button"
            className="p-3 rounded-full border border-gray-300 hover:bg-gray-50 transition-colors shadow-sm "
            aria-label="Sign up with GitHub"
          >
            <FaGithub className="h-5 w-5 text-gray-800" />
          </button>
        </div>

        <div className="text-center text-sm text-gray-500 mt-4">
          Don't have an account?{" "}
          <Link
            to="/User/Register"
            className="font-medium text-emerald-600  focus:outline-none focus:underline"
          >
            Sign up
          </Link>
        </div>
      </form>
    </div>
  );
};

export default Login;
