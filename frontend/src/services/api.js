const API_URL = (import.meta.env.VITE_API_BASE_URL || "/api").replace(/\/+$/, "");
async function request(path, options = {}) {
  let response;
  try { response = await fetch(`${API_URL}${path}`, { headers: { "Content-Type": "application/json", ...options.headers }, ...options }); }
  catch { throw new Error("Unable to reach CareerForge. Check that the backend is running."); }
  const data = await response.json().catch(() => null);
  if (!response.ok) throw new Error(data?.message || data?.error || "Something went wrong. Please try again.");
  return data;
}

export const loginUser = async (email, password) => {
  return request("/users/login", { method: "POST", body: JSON.stringify({ email, password }) });
};

export const registerUser = async (name, email, password) => {
  return request("/users/register", { method: "POST", body: JSON.stringify({ name, email, password }) });
};
export const getCourses = async () => {
  return request("/courses");
};
export const getLessons = (courseId) => request(`/courses/${courseId}/lessons`);
export const getProgress = (courseId, userId) => request(`/courses/${courseId}/progress?userId=${userId}`);
export const setLessonProgress = (lessonId, userId, completed = true) => request(`/lessons/${lessonId}/progress`, { method: "PUT", body: JSON.stringify({ userId, completed }) });
export const getJobs = (filters = {}) => { const query = new URLSearchParams(Object.entries(filters).filter(([, v]) => v !== "" && v !== false)); return request(`/jobs${query.size ? `?${query}` : ""}`); };
export const getJob = (id) => request(`/jobs/${id}`);
export const submitApplication = (application) => request("/applications", { method: "POST", body: JSON.stringify(application) });
export const getMyApplications = (userId) => request(`/applications/user/${userId}`);
export const getApplicationById = (id) => request(`/applications/${id}`);
