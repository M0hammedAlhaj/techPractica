import { CategoryType } from "./data/data";

export type FormInputRegister =
  | "firstName"
  | "lastName"
  | "name"
  | "userEmail"
  | "userPassword";
export interface IRegister {
  name: FormInputRegister;
  placeholder: string;
  type: string;
  label: string;
}
export const RegisterForm: IRegister[] = [
  {
    name: "firstName",
    type: "text",
    placeholder: "First Name",
    label: "First Name",
  },
  {
    name: "lastName",
    type: "text",
    placeholder: "Last Name",
    label: "Last Name",
  },
  {
    name: "name",
    type: "text",
    placeholder: "Username",
    label: "Username",
  },
  {
    name: "userEmail",
    type: "email",
    placeholder: "name@example.com",
    label: "Your email",
  },
  {
    name: "userPassword",
    type: "password",
    placeholder: "••••••••",
    label: "Password",
  },
];

export interface IErrorResponse {
  error?: {
    message?: string;
    [key: string]: any;
  };
  message?: string;
  [key: string]: any;
}
export type FormInputLogin = "userEmail" | "userPassword";
interface ILogin {
  name: FormInputLogin;
  placeholder: string;
  type: string;
  label: string;
}
export const LoginForm: ILogin[] = [
  {
    name: "userEmail",
    type: "email",
    placeholder: "name@example.com",
    label: "Your email",
  },
  {
    name: "userPassword",
    type: "password",
    placeholder: "••••••••",
    label: "Password",
  },
];

export type FormInputRe = "userEmail";

export interface IReset {
  name: FormInputRe;
  placeholder: string;
  type: string;
  label: string;
}

export const ResetPassword: IReset = {
  name: "userEmail",
  type: "email",
  placeholder: "name@example.com",
  label: "Your email",
};

export type FormInputReset = "password" | "confirmPassword";

export interface IResetPas {
  name: FormInputReset;
  placeholder: string;
  type: string;
  label: string;
}

export const ResetinputPassword: IResetPas[] = [
  {
    name: "password",
    type: "password",
    placeholder: "••••••••",
    label: "Password",
  },
  {
    name: "confirmPassword",
    type: "password",
    placeholder: "••••••••",
    label: "Confirm Password",
  },
];
export type ISessionForm = {
  sessionName: string;
  sessionDescription: string;
  category: string;
  technologies: string[];
  fields: string[];
  privateSession: boolean;
};
export type FormInput = "nameSession" | "sessionDescription";

export interface Iinpform {
  name: FormInput;
  placeholder: string;
  type: string;
  label: string;
}
export type System = {
  systemName: string;
};
export interface ISession {
  id: number;
  sessionName: string;
  sessionDescription: string;
  technologies: string[];
  system: CategoryType;
  categories: string[];
  privateSession: boolean;
}
export interface ISessionRes {
  id: number;
  sessionName: string;
  sessionDescription: string;
  technologies: string[];
  system: CategoryType;
  categories: string[];
  isPrivate: boolean;
  ownerName: string;
}

export interface User {
  id: string;
  name: string;
  avatar: string; // URL or path
}

export interface Task {
  id: string;
  content: string;
  title: string;
  priority: "low" | "medium" | "high";
  dueDate?: string;
  createdAt?: string;
  tags?: string[];
  likes?: number;
  comments?: number;
  users?: User[];
  status?: string;
  image?: string;
}

export interface Column {
  id: string;
  title: string;
  tasks: Task[];
}

export interface KanbanBoarde {
  columns: {
    [key: string]: Column;
  };
  columnOrder: string[];
}
