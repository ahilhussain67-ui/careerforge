import { useState } from "react";
import "./applications.css";
import "./audit.css";

export default function AppShell({ user, page, onNavigate, onLogout, children }) {
  const [menuOpen, setMenuOpen] = useState(false);
  const links = [["dashboard", "Dashboard"], ["skills", "Learning"], ["jobs", "Jobs"], ["applications", "Applications"], ["interview", "Practice"], ["resume", "Resume"]];
  const navigate = (destination) => { onNavigate(destination); setMenuOpen(false); };
  const initials = user?.name?.split(" ").map((part) => part[0]).join("").slice(0, 2).toUpperCase();
  return <div className="app-shell"><header className="topbar"><button className="brand" onClick={() => navigate("dashboard")} aria-label="CareerForge home"><span className="brand-mark">C</span><span className="brand-name">Career<span>Forge</span></span></button><button className="nav-toggle" aria-label="Toggle navigation" aria-expanded={menuOpen} onClick={() => setMenuOpen((open) => !open)}><span /><span /><span /></button><nav className={menuOpen ? "nav-open" : ""}>{links.map(([id, label]) => <button key={id} className={page === id ? "nav-link active" : "nav-link"} onClick={() => navigate(id)}>{label}</button>)}</nav><div className="user-menu"><span className="avatar">{initials}</span><span className="user-name">{user?.name?.split(" ")[0]}</span><button className="text-button" onClick={onLogout}>Sign out</button></div></header>{children}</div>;
}
