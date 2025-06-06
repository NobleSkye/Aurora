import { Download, ExternalLink, Shield, Wrench, Github } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import { Separator } from "@/components/ui/separator"

export default function HomePage() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-purple-900 to-slate-900">
      {/* Header */}
      <header className="border-b border-white/10 bg-black/20 backdrop-blur-sm">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-2">
              <div className="w-8 h-8 rounded-lg overflow-hidden">
                <img src="/auerora.png" alt="Aurora" className="w-full h-full object-cover" />
              </div>
              <h1 className="text-2xl font-bold text-white">Aurora</h1>
            </div>
            <Badge variant="secondary" className="bg-purple-500/20 text-purple-200 border-purple-400/30">
              Early Beta
            </Badge>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <section className="py-20 px-4">
        <div className="container mx-auto text-center">
          <div className="max-w-4xl mx-auto">
            <h1 className="text-5xl md:text-7xl font-bold text-white mb-6 bg-gradient-to-r from-purple-400 via-pink-400 to-purple-400 bg-clip-text text-transparent">
              Aurora
            </h1>
            <p className="text-xl md:text-2xl text-slate-300 mb-8 leading-relaxed">
              Your all-in-one tool for enhancing and simplifying Minecraft editing, elevating your building potential to
              new heights.
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center">
              <Button
                size="lg"
                className="bg-gradient-to-r from-purple-600 to-pink-600 hover:from-purple-700 hover:to-pink-700 text-white"
                asChild
              >
                <a href="https://modrinth.com/mod/aurora-utils" target="_blank" rel="noopener noreferrer">
                  <Download className="w-5 h-5 mr-2" />
                  Download on Modrinth
                  <ExternalLink className="w-4 h-4 ml-2" />
                </a>
              </Button>
              <Button
                size="lg"
                variant="outline"
                className="border-purple-400/50 text-purple-200 hover:bg-purple-500/20 hover:text-white hover:border-purple-400"
              >
                View Documentation
              </Button>
            </div>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-16 px-4">
        <div className="container mx-auto">
          <div className="text-center mb-12">
            <h2 className="text-3xl md:text-4xl font-bold text-white mb-4">Features</h2>
            <p className="text-slate-300 text-lg">
              Aurora offers a comprehensive suite of tools designed for real-time, precise adjustments in your Minecraft
              worlds
            </p>
          </div>

          <Card className="bg-black/40 border-white/10 backdrop-blur-sm">
            <CardHeader>
              <CardTitle className="text-white flex items-center gap-2">
                <Wrench className="w-6 h-6 text-purple-400" />
                Builder Mode
              </CardTitle>
              <CardDescription className="text-slate-300">
                Enhance your building experience with powerful tools
              </CardDescription>
            </CardHeader>
            <CardContent>
              <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                {["Fast Place", "Fast Break", "No Clip", "ForcePlace", "Replace", "Tinker", "Freeze Updates"].map(
                  (feature) => (
                    <div key={feature} className="bg-white/5 rounded-lg p-3 text-center">
                      <span className="text-white font-medium">{feature}</span>
                    </div>
                  ),
                )}
              </div>
            </CardContent>
          </Card>
        </div>
      </section>

      {/* Upcoming Features */}
      <section className="py-16 px-4">
        <div className="container mx-auto">
          <div className="text-center mb-12">
            <h2 className="text-3xl md:text-4xl font-bold text-white mb-4">Upcoming Enhancements</h2>
            <p className="text-slate-300 text-lg">We're continuously improving Aurora. Future updates will include:</p>
          </div>

          <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
            {[
              "Iconic button logos for various tools",
              "Flight speed changer",
              "Movement momentum adjuster",
              "Display entities editor",
              "More accessible settings page",
              "Quick update button",
            ].map((feature, index) => (
              <Card key={index} className="bg-black/40 border-white/10 backdrop-blur-sm">
                <CardContent className="p-6">
                  <div className="flex items-start gap-3">
                    <div className="w-2 h-2 bg-purple-400 rounded-full mt-2 flex-shrink-0"></div>
                    <span className="text-white">{feature}</span>
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* EULA Section */}
      <section className="py-16 px-4">
        <div className="container mx-auto">
          <Card className="bg-black/40 border-white/10 backdrop-blur-sm">
            <CardHeader>
              <CardTitle className="text-white flex items-center gap-2">
                <Shield className="w-6 h-6 text-red-400" />
                End User License Agreement (EULA)
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              <p className="text-slate-300">By installing Aurora, you agree to the following terms:</p>
              <div className="bg-red-500/10 border border-red-500/20 rounded-lg p-4">
                <p className="text-red-200 font-medium">
                  <strong>Important:</strong> You may NOT share, distribute, or redistribute Aurora in any form, whether
                  modified or unmodified. If you wish to share Aurora with others, please direct them to the original
                  source on Modrinth.
                </p>
              </div>
              <ul className="text-slate-300 space-y-2 list-disc list-inside">
                <li>Aurora is provided "as is" during the early beta phase</li>
                <li>Use Aurora at your own risk in your Minecraft worlds</li>
                <li>Report bugs and issues through our official channels</li>
                <li>Respect the intellectual property rights of the developers</li>
              </ul>
            </CardContent>
          </Card>
        </div>
      </section>

      {/* Footer */}
      <footer className="py-12 px-4 border-t border-white/10">
        <div className="container mx-auto text-center">
          <div className="flex items-center justify-center gap-2 mb-4">
            <div className="w-6 h-6 rounded-lg overflow-hidden">
              <img src="/aurora.png" alt="Aurora" className="w-full h-full object-cover" />
            </div>
            <span className="text-white font-bold text-lg">Aurora</span>
          </div>
          <p className="text-slate-400 mb-4">Elevating your Minecraft building experience</p>
          <Separator className="bg-white/10 mb-6" />
          <div className="flex flex-col sm:flex-row items-center justify-center gap-4">
            <Button variant="ghost" className="text-slate-400 hover:text-white" asChild>
              <a href="https://modrinth.com/mod/aurora-utils" target="_blank" rel="noopener noreferrer">
                Modrinth Page
                <ExternalLink className="w-4 h-4 ml-2" />
              </a>
            </Button>
            <Button variant="ghost" className="text-slate-400 hover:text-white" asChild>
              <a href="https://github.com/NobleSkye" target="_blank" rel="noopener noreferrer">
                <Github className="w-4 h-4 mr-2" />
                GitHub
                <ExternalLink className="w-4 h-4 ml-2" />
              </a>
            </Button>
          </div>
          <p className="text-slate-500 text-sm mt-6">Happy building! 🚀</p>
        </div>
      </footer>
    </div>
  )
}
