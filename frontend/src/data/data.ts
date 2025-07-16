import { IconType } from "react-icons";

export interface Category {
  title: CategoryType;
  Icon: IconType;
  style: string;
}

export interface Feature {
  title: string;
  description: string;
  Icon: IconType;
  style: string;
}
import {
  FaCode,
  FaShieldAlt,
  FaMobileAlt,
  FaTools,
  FaRobot,
  FaGamepad,
  FaBug,
  FaProjectDiagram,
} from "react-icons/fa";
export const categories: Category[] = [
  {
    title: "Web Development",
    Icon: FaCode,
    style: "text-blue-600 w-6 h-6",
  },
  {
    title: "Cybersecurity",
    Icon: FaShieldAlt,
    style: "text-red-600 w-6 h-6",
  },
  {
    title: "Mobile Development",
    Icon: FaMobileAlt,
    style: "text-green-600 w-6 h-6",
  },

  {
    title: "Artificial Intelligence",
    Icon: FaRobot,
    style: "text-teal-600 w-6 h-6",
  },

  {
    title: "Game Development",
    Icon: FaGamepad,
    style: "text-purple-600 w-6 h-6",
  },
];
export const features: Feature[] = [
  {
    title: "Real-world Projects",
    description:
      "Let students apply knowledge in hands-on scenarios that simulate real job challenges.",
    Icon: FaProjectDiagram,
    style: "text-blue-600 w-6 h-6",
  },
  {
    title: "Skill Evaluation",
    description:
      "Assess technical and soft skills effectively through tailored projects and tasks.",
    Icon: FaTools,
    style: "text-yellow-500 w-6 h-6",
  },

  {
    title: "Progress Tracking",
    description:
      "Monitor student development and project completion status with detailed analytics.",
    Icon: FaBug,
    style: "text-gray-700 w-6 h-6",
  },
];

interface Itags {
  Mcolor: string;
  txt: string;
}
export const tags: Itags[] = [
  {
    Mcolor: "bg-red-200 text-red-800",
    txt: "Java",
  },
  {
    Mcolor: "bg-green-700",
    txt: "Spring Boot",
  },
  {
    Mcolor: "bg-cyan-500",
    txt: "React",
  },
  {
    Mcolor: "bg-blue-800",
    txt: "Type Script",
  },
  {
    Mcolor: "bg-gray-500",
    txt: "Figma",
  },
];

export type CategoryType =
  | "Web Development"
  | "Cybersecurity"
  | "Game Development"
  | "Artificial Intelligence"
  | "Mobile Development";
export const skills = [
  "HTML",
  "CSS",
  "JavaScript",
  "React",
  "TypeScript",
  "Node.js",
];
