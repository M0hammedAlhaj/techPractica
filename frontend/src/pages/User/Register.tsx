import { useState } from "react";
import Inputs from "../../components/ui/Input";
import { BiHide, BiShow } from "react-icons/bi";
import { SubmitHandler, useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import { registerSchema } from "../../validation";
import { IErrorResponse, IRegister, RegisterForm } from "../../interfaces";
import ErrorMsg from "../../components/ui/ErrorMsg";
import Button from "../../components/ui/Buttom";
import { Link, useNavigate } from "react-router-dom";
import axiosInstance from "../../config/axios.config";
import toast from "react-hot-toast";
import { AxiosError } from "axios";
import { FaGoogle, FaGithub } from "react-icons/fa";
import { FiLoader } from "react-icons/fi";
const Register = () => {
  document.title = "TechPractica | Register";

  const navigate = useNavigate();

  type IFormInput = {
    firstName?: string;
    lastName?: string;
    name: string;
    userEmail: string;
    userPassword: string;
  };

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<IFormInput>({
    resolver: yupResolver(registerSchema),
  });

  const [showPassword, setShowPassword] = useState(false);

  const onSubmit: SubmitHandler<IFormInput> = async (data) => {
    try {
      await axiosInstance.post("/authenticated/registration", data);
      toast.success("Registration successful!", {
        position: "top-center",
        duration: 2000,
      });
      setTimeout(() => navigate("/User"), 1500);
    } catch (error) {
      const ErrorObj = error as AxiosError<IErrorResponse>;
      toast.error(`${ErrorObj.response?.data.message}`, {
        position: "top-center",
        duration: 2000,
      });
    }
  };

  const renderInputField = ({ label, name, placeholder, type }: IRegister) => {
    const isPasswordField = type === "password";
    return (
      <div className="mb-4" key={name}>
        <label
          hidden={name === "lastName" || name === "firstName"}
          htmlFor={label}
          className="block text-sm font-medium text-gray-700 mb-1"
        >
          {label}
        </label>
        <div className="relative">
          <Inputs
            hidden={name === "lastName" || name === "firstName"}
            defaultValue={name === "lastName" || name === "firstName" ? "" : ""}
            id={label}
            type={isPasswordField ? (showPassword ? "text" : "password") : type}
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
  };

  return (
    <div className="max-w-md mx-auto bg-white p-8 rounded-xl shadow-lg border border-gray-200">
      <div className="text-center mb-8">
        <h1 className="text-3xl font-bold text-gray-900 ">
          Create Your Account
        </h1>
        <p className="text-gray-500 mt-2">Join our community to get started</p>
      </div>

      <form onSubmit={handleSubmit(onSubmit)} className="space-y-5">
        {RegisterForm.map(renderInputField)}

        <Button
          type="submit"
          disabled={isSubmitting}
          className="w-full py-3 px-4 bg-[#42D5AE] hover:bg-[#38b28d] text-white font-medium rounded-lg shadow-sm transition-all duration-300 flex items-center justify-center focus:ring-2 focus:ring-emerald-500 focus:ring-offset-2"
          aria-label={isSubmitting ? "Creating your account" : "Create account"}
        >
          {isSubmitting ? (
            <>
              <FiLoader className="animate-spin -ml-1 mr-3 h-5 w-5 text-white" />
              Creating account...
            </>
          ) : (
            "Create Account"
          )}
        </Button>

        <div className="relative my-6">
          <div className="absolute inset-0 flex items-center">
            <div className="w-full border-t border-gray-300"></div>
          </div>
          <div className="relative flex justify-center text-sm">
            <span className="px-2 bg-white text-gray-500">
              Or continue with
            </span>
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

        <div className="text-center text-sm text-gray-500 mt-6">
          Already have an account?{" "}
          <Link
            to="/User"
            className="font-medium text-emerald-600 transition-colors focus:outline-none focus:underline"
          >
            Sign in
          </Link>
        </div>
      </form>
    </div>
  );
};

export default Register;
