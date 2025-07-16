import React from "react";
import { Task } from "../../interfaces";
import { FaCommentDots } from "react-icons/fa";
import userImgg from "../../assets/user.png";

interface KanbanCardProps {
  task: Task;
}

const KanbanCard: React.FC<KanbanCardProps> = ({ task }) => {
  let daysLeft = "";
  if (task.dueDate) {
    const due = new Date(task.dueDate);
    const now = new Date();
    const diff = Math.ceil(
      (due.getTime() - now.getTime()) / (1000 * 60 * 60 * 24)
    );
    daysLeft = diff > 0 ? `${diff} days left` : "Due";
  }

  return (
    <div className="bg-white border border-gray-200 rounded-md shadow-sm hover:shadow-md transition-shadow p-4 mb-4">
      <div className="h-44 flex flex-col justify-between overflow-hidden">
        <div>
          <div className="flex justify-between items-center text-xs text-gray-500 mb-2">
            <span>Created - {task.createdAt}</span>
            {task.dueDate && <span>{daysLeft}</span>}
          </div>
          <div className="flex flex-wrap gap-1 mb-2">
            {task.tags?.map((tag, idx) => (
              <span
                key={idx}
                className={
                  "px-3 py-1 text-xs bg-[#42D5AE]/10 text-[#022639] border border-[#42D5AE] rounded-md   cursor-pointer"
                }
              >
                {tag}
              </span>
            ))}
          </div>
          <div className="font-semibold text-base mb-1 line-clamp-1">
            {task.title}
          </div>
          <div className="text-xs text-gray-500 mb-3 line-clamp-2">
            {task.content}
          </div>
        </div>
        <div className="flex justify-between items-end mt-2">
          <div className="flex items-center gap-3 text-xs">
            <span className="flex items-center gap-1 text-gray-500 font-semibold">
              <FaCommentDots className="inline-block" />
              {task.comments?.toString().padStart(2, "0")}
            </span>
          </div>
          <div className="flex -space-x-2">
            {task.users?.map((user, idx) => (
              <img
                key={user.id}
                src={userImgg}
                alt={user.name}
                className="w-6 h-6 rounded-full border-2 border-white shadow-sm"
                style={{ zIndex: 10 - idx }}
              />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default KanbanCard;
